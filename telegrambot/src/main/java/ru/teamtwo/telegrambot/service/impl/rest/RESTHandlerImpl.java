package ru.teamtwo.telegrambot.service.impl.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CartItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CustomerClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductOfferClient;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class RESTHandlerImpl implements RESTHandler {
    private final CartItemClient cartItemClient;
    private final CustomerClient customerClient;
    private final OrderClient orderClient;
    private final OrderItemClient orderItemClient;
    private final ProductOfferClient productOfferClient;

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

        CustomerState customerState = CustomerState.builder()
                .address(customerDto.address())
                .chatId(customerDto.chatId())
                .stage(Stage.valueOf(customerDto.state()))
                .searchQuery(customerDto.searchQuery())
                .sortingTypeField(ProductOfferController.SortingTypeField.valueOf(customerDto.sortingTypeField()))
                .sortingTypeAscDesc(ProductOfferController.SortingTypeAscDesc.valueOf(customerDto.sortingTypeAscDesc()))
                .offset(customerDto.offset())
                .limit(customerDto.limit())
                .cart(cartItemsByCustomer)
                .currentProductId(customerDto.currentProductId())
                .orders(customerOrders)
                .build();

        return customerState;
    }

    @Override
    public void saveCustomerState(CustomerState customerState) throws RESTHandlerException {
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

        Map<Integer, List<OrderItemDto>> orderItemDtosByOrderId = customerState.getOrders().stream()
                .map(CustomerOrder::getOrderItemDtos)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(OrderItemDto::orderId));

        for (Map.Entry<Integer, List<OrderItemDto>> entry : orderItemDtosByOrderId.entrySet()) {
            checkResponseEntity(orderItemClient.saveAllByOrder(entry.getKey(), Set.copyOf(entry.getValue())));
        }

        Set<OrderDto> orderDtos = customerState.getOrders().stream()
                .map(CustomerOrder::getOrderDto)
                .collect(Collectors.toSet());

        checkResponseEntity(orderClient.saveAllByCustomer(customerState.getUser().getId(), orderDtos));

        checkResponseEntity(cartItemClient.saveAllByCustomer(customerState.getUser().getId(), customerState.getCart()));

        checkResponseEntity(customerClient.save(customerDto));
    }

    @Override
    public Set<ProductOfferDto> queryProducts(ProductOfferController.ProductQuery productQuery) throws RESTHandlerException {
        Set<ProductOfferDto> productList = fromResponseEntity(productOfferClient.query(productQuery));

        return productList;
    }

    private void checkResponseEntity(ResponseEntity<?> responseEntity) throws RESTHandlerException {
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();
        if(responseEntityStatusCode != HttpStatus.OK){
            throw new RESTHandlerException();
        }
    }

    private <T> T fromResponseEntity(ResponseEntity<T> responseEntity) throws RESTHandlerException {
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();
        if(responseEntityStatusCode != HttpStatus.OK){
            throw new RESTHandlerException();
        }
        return responseEntity.getBody();
    }
}
