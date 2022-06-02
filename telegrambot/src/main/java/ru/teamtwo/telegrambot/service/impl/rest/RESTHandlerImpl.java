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
import ru.teamtwo.telegrambot.mapper.CustomerStateMapper;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CartItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CustomerClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductOfferClient;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RESTHandlerImpl implements RESTHandler {
    private final CartItemClient cartItemClient;
    private final CustomerClient customerClient;
    private final OrderClient orderClient;
    private final OrderItemClient orderItemClient;
    private final ProductOfferClient productOfferClient;
    private final CustomerStateMapper customerStateMapper;

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

        return customerStateMapper.convertToEntity(customerDto, cartItemsByCustomer, customerOrders);
    }

    @Override
    public void saveCustomerState(CustomerState customerState) throws RESTHandlerException {
        checkResponseEntity(customerClient.save(Collections.singleton(customerStateMapper.convertToDto(customerState))));

        checkResponseEntity(cartItemClient.save(customerState.getCart()));

        checkResponseEntity(orderItemClient.save(customerState.getOrders()
                .stream()
                .map(CustomerOrder::getOrderItemDtos)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())));

        checkResponseEntity(orderClient.save(customerState.getOrders()
                .stream()
                .map(CustomerOrder::getOrderDto)
                .collect(Collectors.toSet())));
    }

    @Override
    public Set<ProductOfferDto> queryProducts(ProductOfferController.ProductQuery productQuery) throws RESTHandlerException {
        return fromResponseEntity(productOfferClient.query(productQuery));
    }

    private void checkResponseEntity(ResponseEntity<?> responseEntity) throws RESTHandlerException {
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();
        if(responseEntityStatusCode != HttpStatus.OK){
            throw new RESTHandlerException();
        }
    }

    private <T> T fromResponseEntity(ResponseEntity<T> responseEntity) throws RESTHandlerException {
        checkResponseEntity(responseEntity);
        return responseEntity.getBody();
    }
}
