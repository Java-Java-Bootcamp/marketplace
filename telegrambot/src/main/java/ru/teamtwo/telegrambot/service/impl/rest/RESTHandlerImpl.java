package ru.teamtwo.telegrambot.service.impl.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.product.ProductSearchHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CartItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CustomerClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductOfferClient;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RESTHandlerImpl implements RESTHandler {
    private final CartItemClient cartItemClient;
    private final CustomerClient customerClient;
    private final OrderClient orderClient;
    private final OrderItemClient orderItemClient;
    private final ProductOfferClient productOfferClient;



    private <T> T fromResponseEntity(ResponseEntity<T> responseEntity) throws RESTHandlerException {
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();
        if(responseEntityStatusCode != HttpStatus.OK){
            throw new RESTHandlerException();
        }
    }

    @Override
    public CustomerState getCustomerState(Long userId) throws RESTHandlerException {
        CustomerDto customerDto = fromResponseEntity(customerClient.get(userId));
        Set<CartItemDto> cartItemsByCustomer = fromResponseEntity(cartItemClient.getAllByCustomer(userId));
        Set<OrderDto> ordersByCustomer = fromResponseEntity(orderClient.getAllByCustomer(userId));
        Set<CustomerOrder> customerOrders = new HashSet<>();
        for (OrderDto orderDto : ordersByCustomer) {
            Set<OrderItemDto> orderItemByOrder = fromResponseEntity(orderItemClient.getAllByOrder(orderDto.id()));
            customerOrders.add(new CustomerOrder(orderDto, orderItemByOrder));
        }

        Set<OrderDto> orderDtoSet = new HashSet<>();

        CustomerState customerState = CustomerState.builder()
                .address(customerDto.address())
                .chatId(customerDto.chatId())
                .stage(Stage.valueOf(customerDto.state()))
                .searchQuery(customerDto.searchQuery())
                .sortingTypeField(ProductSearchHandler.SortingTypeField.valueOf(customerDto.sortingTypeField()))
                .sortingTypeAscDesc(ProductSearchHandler.SortingTypeAscDesc.valueOf(customerDto.sortingTypeAscDesc()))
                .offset(customerDto.offset())
                .limit(customerDto.limit())
                .cart(cartItemsByCustomer)
                .currentProductId(customerDto.currentProductId())
                .build();

        return customerState;
    }

    @Override
    public void saveCustomerState(CustomerState customerState) {
        CustomerDto customerDto = new CustomerDto(
                customerState.getUser().getId(),
                customerState.getName(),
                customerState.getAddress(),
                customerState.getChatId(),
                customerState.getStage().toString(),
                customerState.getSearchQuery(),
                customerState.getSortingTypeField().toString(),
                customerState.getSortingTypeAscDesc().toString(),
                customerState.getOffset(),
                customerState.getLimit(),
                customerState.getCurrentProductId()
        );

        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto(
                new HashSet<>()
        );
        customerState.getCart().forEach((key, value) -> {
            CartItemDto dto = new CartItemDto(
                    null,
                    customerState.getUser().getId(),
                    key,
                    value
            );
            cartItemArrayDto.cartItemDtoList().add(dto);
        });
        customerState.getOrderDtoSet().forEach(orderDto -> {
            orderClient.save(orderDto);
        });


        ResponseEntity<?> responseEntity = cartItemClient.saveCartState(customerState.getUser().getId(), cartItemArrayDto);

        String status = customerClient.save(customerDto).getBody().toString();
    }

    @Override
    public Set<ProductDto> queryProducts(ProductSearchHandler.ProductQuery productQuery) {
        Set<ProductDto> productList = productOfferClient.get(
                productQuery.query(),
                productQuery.offset(),
                productQuery.limit(),
                productQuery.sortingTypeAscDesc()+"_"+productQuery.sortingTypeField().toString().replace("_",".")
        );

        return productList;
    }
}
