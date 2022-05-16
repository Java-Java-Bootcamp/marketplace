package ru.teamtwo.telegrambot.service.bot.handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.client.MarketplaceClient;
import ru.teamtwo.telegrambot.client.customer.CartItemClient;
import ru.teamtwo.telegrambot.client.customer.CustomerClient;
import ru.teamtwo.telegrambot.client.customer.OrderClient;
import ru.teamtwo.telegrambot.client.customer.OrderItemClient;
import ru.teamtwo.telegrambot.client.product.ProductOfferClient;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.model.sorting.SortingTypeAscDesc;
import ru.teamtwo.telegrambot.model.sorting.SortingTypeField;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RESTHandler {
    private final CartItemClient cartItemClient;
    private final CustomerClient customerClient;
    private final MarketplaceClient marketplaceClient;
    private final OrderClient orderClient;
    private final OrderItemClient orderItemClient;
    private final ProductOfferClient productOfferClient;

    public void saveCartState(@NonNull CustomerState customerState){
        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto();
        cartItemArrayDto.setCartItemDtoList(new HashSet<>());

        customerState.getCart().forEach((key, value) -> {
            CartItemDto dto = new CartItemDto();
            dto.setProductId(key);
            dto.setQuantity(value);
            cartItemArrayDto.getCartItemDtoList().add(dto);
        });

        ResponseEntity<?> responseEntity = cartItemClient.saveCartState(Math.toIntExact(customerState.getUser().getId()), cartItemArrayDto);
    }

    /**
     * Запрашивает тележку пользователя и сразу вводит её в userState.
     */
    public void updateCustomerCartFromServer(CustomerState customerState){
        ((CartItemArrayDto) Objects.requireNonNull(cartItemClient.getCartState(Math.toIntExact(customerState.getUser().getId()))
                .getBody()))
                .getCartItemDtoList()
                .forEach(item -> {
                    customerState.getCart().put(item.getProductId(), item.getQuantity());
                });
    }

    /**
     * Запрашивает ProductDTO на сервере, не производя над ним никаких действий.
     */
    public ProductDto getProductDTOById(Integer id){
        return productOfferClient.get(id);
    }

    /**
     * Берет тележку пользователя и отправляет её на сервер в виде заказа.
     */
    public void postNewOrderFromUserCart(CustomerState customerState){
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(Math.toIntExact(customerState.getUser().getId()));
        //orderDto.setCreatedOn(LocalDate.of(2000,10,10)); //TODO: подчинить дату

        Integer newOrderId = Integer.valueOf(Objects.requireNonNull(orderClient.post(orderDto).getBody()).toString());

        customerState.getCart().forEach((key, value) -> {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setOrderId(newOrderId);
            orderItemDto.setProductOfferId(key);
            orderItemDto.setQuantity(value);
        });
    }

    public Optional<CustomerDto> getCustomerDTO(CustomerState customerState){
        Optional<CustomerDto> dto = Optional.empty();

        try {
            CustomerDto customerDto = customerClient.get(Math.toIntExact(customerState.getUser().getId()));

            dto = Optional.of(customerDto);
        }catch (Exception e){
            log.error("getCustomerInfo error: {}", e.getMessage());
        }

        return dto;
    }

    /**
     * Отправляет CustomerState на сервер.
     */
    public void updateCustomerInfoFromServer(CustomerState customerState){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(Math.toIntExact(customerState.getUser().getId()));
        customerDto.setAddress(customerState.getAddress());
        customerDto.setName(customerState.getUser().getUserName());

        String status = customerClient.post(customerDto).getBody().toString();
    }

    /**
     * Запрашивает список товаров через REST API с указанными параметрами.
     * @param filter Текстовый фильтр
     * @param sortingTypeField Поле по которому будет сортировка
     * @param ascDesc Сортировка по возрастающей/убывающей
     * @param offset Сдвиг от первого товара в результатах поиска
     * @param limit Максимальное кол-во товаров в листе
     * @return Отсортированный, отфильтрованный список товаров
     */
    public List<ProductDto> searchProducts(String filter, SortingTypeField sortingTypeField, SortingTypeAscDesc ascDesc, int offset, int limit){
        List<ProductDto> productList = marketplaceClient.getProductOffersByProductName(filter, offset, limit, ascDesc+"_"+ sortingTypeField.toString().replace("_", "."));

        return productList;
    }
}
