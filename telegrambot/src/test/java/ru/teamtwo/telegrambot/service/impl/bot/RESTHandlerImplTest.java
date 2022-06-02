package ru.teamtwo.telegrambot.service.impl.bot;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.telegrambot.CustomerStateTestUtils;
import ru.teamtwo.telegrambot.mapper.CustomerStateMapper;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.impl.rest.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CartItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CustomerClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductOfferClient;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class RESTHandlerImplTest {
    static final Long USER_ID = 12345L;

    @Mock
    Set<CartItemDto> testCart = new HashSet<>();
    Set<OrderDto> testOrders = new HashSet<>();
    Set<CustomerOrder> testCustomerOrders = new HashSet<>();
    Set<ProductDto> testQueryResult = new HashSet<>();
    CustomerState filledCustomerState;
    CustomerDto customerDto;
    @Mock
    CustomerStateMapper customerStateMapper;
    @Mock
    ResponseEntity responseEntity;
    @Mock
    ResponseEntity responseEntityWithOrders;
    @Mock
    ResponseEntity responseEntityWithCartItems;
    @Mock
    CartItemClient cartItemClient;
    @Mock
    CustomerClient customerClient;
    @Mock
    OrderClient orderClient;
    @Mock
    OrderItemClient orderItemClient;
    @Mock
    ProductOfferClient productOfferClient;
    @InjectMocks
    RESTHandlerImpl restHandler;

    @BeforeEach
    void setUp() {
        testCart.add(new CartItemDto(1L, 1L, 1L, 1));
        testOrders.add(new OrderDto(1L, 1L, Instant.now()));

        filledCustomerState = CustomerStateTestUtils.getCustomerState();
        customerDto = CustomerStateTestUtils.getCustomerDto();

        Mockito.when(customerStateMapper.convertToDto(filledCustomerState)).thenReturn(customerDto);
        Mockito.when(customerStateMapper.convertToEntity(Mockito.eq(customerDto), Mockito.eq(testCart), Mockito.any())).thenReturn(filledCustomerState);

        Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(responseEntityWithOrders.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(responseEntityWithOrders.getBody()).thenReturn(testOrders);
        Mockito.when(responseEntityWithCartItems.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(responseEntityWithCartItems.getBody()).thenReturn(testCart);

        Mockito.when(cartItemClient.getAllByCustomer(Mockito.any())).thenReturn(responseEntityWithCartItems);
        Mockito.when(orderClient.getAllByCustomer(Mockito.any())).thenReturn(responseEntityWithOrders);
        Mockito.when(orderItemClient.getAllByOrder(Mockito.any())).thenReturn(responseEntity);
        Mockito.when(customerClient.save(Mockito.any())).thenReturn(responseEntity);
        Mockito.when(cartItemClient.save(Mockito.any())).thenReturn(responseEntity);
        Mockito.when(orderClient.save(Mockito.any())).thenReturn(responseEntity);
        Mockito.when(orderItemClient.save(Mockito.any())).thenReturn(responseEntity);
        Mockito.when(productOfferClient.query(Mockito.any())).thenReturn(responseEntity);
    }

    @Test
    void getCustomerState() throws RESTHandlerException {
        ResponseEntity<CustomerDto> response = new ResponseEntity<>(customerDto, HttpStatus.OK);
        Mockito.when(customerClient.get(USER_ID)).thenReturn(response);

        CustomerState customerState = restHandler.getCustomerState(USER_ID);

        Mockito.verify(customerClient, Mockito.times(1)).get(USER_ID);
        Assertions.assertThat(filledCustomerState.getUserId()).isEqualTo(customerState.getUserId());
    }

    @Test
    void saveCustomerState() throws RESTHandlerException {
        restHandler.saveCustomerState(filledCustomerState);

        Mockito.verify(customerClient, Mockito.times(1)).save((Set<CustomerDto>) MockitoHamcrest.argThat(Matchers.hasItem(customerDto)));
        Mockito.verify(cartItemClient, Mockito.times(1)).save(filledCustomerState.getCart());
        Mockito.verify(orderClient, Mockito.times(1)).save(filledCustomerState.getOrders().stream().map(CustomerOrder::getOrderDto).collect(Collectors.toSet()));
        Mockito.verify(orderItemClient, Mockito.times(1)).save(filledCustomerState.getOrders().stream().map(CustomerOrder::getOrderItemDtos).flatMap(Collection::stream).collect(Collectors.toSet()));
    }

    @Test
    void queryProducts() throws RESTHandlerException {
        ProductOfferController.ProductQuery productQuery = new ProductOfferController.ProductQuery("query", ProductOfferController.SortingTypeField.PRODUCT_RATING, ProductOfferController.SortingTypeAscDesc.ASC, 0, 5);

        Set<ProductOfferDto> productQueryResult = restHandler.queryProducts(productQuery);

        Mockito.verify(productOfferClient, Mockito.times(1)).query(productQuery);
    }

}