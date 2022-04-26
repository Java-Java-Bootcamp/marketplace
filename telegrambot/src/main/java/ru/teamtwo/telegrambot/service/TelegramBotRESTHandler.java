package ru.teamtwo.telegrambot.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.user.CartItemArrayDto;
import ru.teamtwo.core.dtos.user.CartItemDto;
import ru.teamtwo.core.dtos.user.CustomerDto;
import ru.teamtwo.core.dtos.user.OrderDto;
import ru.teamtwo.core.dtos.user.OrderItemDto;
import ru.teamtwo.core.dtos.ProductDTO;
import ru.teamtwo.telegrambot.client.CartItemController;
import ru.teamtwo.telegrambot.client.CustomerController;
import ru.teamtwo.telegrambot.client.MarketplaceController;
import ru.teamtwo.telegrambot.client.OrderController;
import ru.teamtwo.telegrambot.client.OrderItemController;
import ru.teamtwo.telegrambot.client.ProductOfferController;
import ru.teamtwo.telegrambot.model.UserState;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBotRESTHandler {
    private final CartItemController cartItemController;
    private final CustomerController customerController;
    private final MarketplaceController marketplaceController;
    private final OrderController orderController;
    private final OrderItemController orderItemController;
    private final ProductOfferController productOfferController;

    public void saveCartState(@NonNull UserState userState){
        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto();
        cartItemArrayDto.setCartItemDtoList(new HashSet<>());

        userState.getCart().forEach((key, value) -> {
            CartItemDto dto = new CartItemDto();
            dto.setProductId(key);
            dto.setQuantity(value);
            cartItemArrayDto.getCartItemDtoList().add(dto);
        });

        ResponseEntity<?> responseEntity = cartItemController.saveCartState(Math.toIntExact(userState.getUser().getId()), cartItemArrayDto);
    }

    /**
     * Запрашивает тележку пользователя и сразу вводит её в userState.
     */
    public void getCartState(UserState userState){
        ((CartItemArrayDto) Objects.requireNonNull(cartItemController.getCartState(Math.toIntExact(userState.getUser().getId()))
                .getBody()))
                .getCartItemDtoList()
                .forEach(item -> {
                    userState.getCart().put(item.getProductId(), item.getQuantity());
                });
    }

    /**
     * Запрашивает ProductDTO на сервере, не производя над ним никаких действий.
     */
    public ProductDTO getProductDTOById(Integer id){
        return productOfferController.get(id);
    }

    /**
     * Берет тележку пользователя и отправляет её на сервер в виде заказа.
     */
    public void postNewOrderFromUserCart(UserState userState){
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(Math.toIntExact(userState.getUser().getId()));
        //orderDto.setCreatedOn(LocalDate.of(2000,10,10)); //TODO: подчинить дату

        Integer newOrderId = Integer.valueOf(Objects.requireNonNull(orderController.post(orderDto).getBody()).toString());

        userState.getCart().forEach((key, value) -> {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setOrderId(newOrderId);
            orderItemDto.setProductOfferId(key);
            orderItemDto.setQuantity(value);
        });
    }

    public Optional<CustomerDto> getCustomerInfo(UserState userState){
        Optional<CustomerDto> dto = Optional.empty();

        try {
            CustomerDto customerDto = customerController.get(Math.toIntExact(userState.getUser().getId()));

            dto = Optional.of(customerDto);
        }catch (Exception e){
            log.error("getCustomerInfo error: {}", e.getMessage());
        }

        return dto;
    }

    public void updateCustomerInfo(UserState userState){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(Math.toIntExact(userState.getUser().getId()));
        customerDto.setAddress(userState.getAddress());
        customerDto.setName(userState.getUser().getUserName());

        String status = customerController.post(customerDto).getBody().toString();
    }

    /**
     * Запрашивает список товаров через REST API с указанными параметрами.
     * @param filter Текстовый фильтр
     * @param sortingType Поле по которому будет сортировка
     * @param ascDesc Сортировка по возрастающей/убывающей
     * @param offset Сдвиг от первого товара в результатах поиска
     * @param limit Максимальное кол-во товаров в листе
     * @return Отсортированный, отфильтрованный список товаров
     */
    public List<ProductDTO> getSortedProductsByFilterWithOffsetAndLimit(String filter, SortingType sortingType, SortingTypeAscDesc ascDesc, int offset, int limit){
        List<ProductDTO> productList = marketplaceController.getProductOffersByProductName(filter, offset, limit, ascDesc+"_"+ sortingType.toString().replace("_", "."));

        return productList;
    }
}
