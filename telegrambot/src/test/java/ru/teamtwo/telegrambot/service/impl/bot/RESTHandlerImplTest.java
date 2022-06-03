package ru.teamtwo.telegrambot.service.impl.bot;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.core.dtos.product.StoreDto;
import ru.teamtwo.telegrambot.CustomerStateTestUtils;
import ru.teamtwo.telegrambot.mapper.CustomerStateMapper;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.model.product.Product;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.impl.rest.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CartItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CustomerClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductOfferClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.StoreClient;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RESTHandlerImplTest {
    static final Long USER_ID = 12345L;

    @Mock
    Set<CartItemDto> testCart = new HashSet<>();
    Set<OrderDto> testOrders = new HashSet<>();
    Set<OrderItemDto> testOrderItems = new HashSet<>();
    Set<ProductOfferDto> testProductOffers = new HashSet<>();
    Set<CustomerOrder> testCustomerOrders = new HashSet<>();
    Set<ProductDto> testQueryResult = new HashSet<>();
    CustomerState filledCustomerState;
    CustomerDto customerDto;
    @Mock
    CustomerStateMapper customerStateMapper;
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
    @Mock
    StoreClient storeClient;
    @Mock
    ProductClient productClient;
    @InjectMocks
    RESTHandlerImpl restHandler;

    @BeforeEach
    void setUp() {
        testCart.add(new CartItemDto(1L, 1L, 1L, 1));
        testOrders.add(new OrderDto(1L, 1L, Instant.now()));
        testOrderItems.add(new OrderItemDto(1L, 1L, 1L, 10));
        testProductOffers.add(new ProductOfferDto(1L, 1L, 1L, 10));

        filledCustomerState = CustomerStateTestUtils.getCustomerState();
        customerDto = CustomerStateTestUtils.getCustomerDto();

        when(customerStateMapper.convertToDto(filledCustomerState)).thenReturn(customerDto);
        when(customerStateMapper.convertToEntity(eq(customerDto), eq(testCart), any())).thenReturn(filledCustomerState);

        when(cartItemClient.getAllByCustomer(any())).thenReturn(createResponseEntity(testCart));
        when(orderClient.getAllByCustomer(any())).thenReturn(createResponseEntity(testOrders));
        when(orderItemClient.getAllByOrder(any())).thenReturn(createResponseEntity(testOrderItems));
        when(customerClient.save(any())).thenReturn(createResponseEntity(null));
        when(cartItemClient.save(any())).thenReturn(createResponseEntity(null));
        when(orderClient.save(any())).thenReturn(createResponseEntity(null));
        when(orderItemClient.save(any())).thenReturn(createResponseEntity(null));
        when(productOfferClient.query(any())).thenReturn(createResponseEntity(testProductOffers));
        when(storeClient.get(any())).thenReturn(createResponseEntity(new StoreDto(1L, "name", 1)));
        when(productClient.get(any())).thenReturn(createResponseEntity(new ProductDto(1L, "name", "name", "name", "name", "name", 1, 1)));
    }

    private ResponseEntity createResponseEntity(Object body){
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @Test
    void getCustomerState() throws RESTHandlerException {
        ResponseEntity<CustomerDto> response = new ResponseEntity<>(customerDto, HttpStatus.OK);
        when(customerClient.get(USER_ID)).thenReturn(response);

        CustomerState customerState = restHandler.getCustomerState(USER_ID);

        verify(customerClient, times(1)).get(USER_ID);
        assertThat(filledCustomerState.getUserId()).isEqualTo(customerState.getUserId());
    }

    @Test
    void saveCustomerState() throws RESTHandlerException {
        restHandler.saveCustomerState(filledCustomerState);

        verify(customerClient, times(1)).save((Set<CustomerDto>) MockitoHamcrest.argThat(Matchers.hasItem(customerDto)));
        verify(cartItemClient, times(1)).save(filledCustomerState.getCart());
        verify(orderClient, times(1)).save(filledCustomerState.getOrders().stream().map(CustomerOrder::getOrderDto).collect(Collectors.toSet()));
        verify(orderItemClient, times(1)).save(filledCustomerState.getOrders().stream().map(CustomerOrder::getOrderItemDtos).flatMap(Collection::stream).collect(Collectors.toSet()));
    }

    @Test
    void queryProducts() throws RESTHandlerException {
        ProductOfferController.ProductQuery productQuery = new ProductOfferController.ProductQuery("query", ProductOfferController.SortingTypeField.PRODUCT_RATING, ProductOfferController.SortingTypeAscDesc.ASC, 0, 5);

        Set<Product> productQueryResult = restHandler.queryProducts(productQuery);

        verify(productOfferClient, times(1)).query(productQuery);
        verify(productClient, times(1)).get(1L);
        verify(storeClient, times(1)).get(1L);

        assertThat(productQueryResult).hasSize(1);
        Product product = productQueryResult.stream().collect(Collectors.toList()).get(0);
        assertThat(product.productOfferDto().id()).isEqualTo(1L);
    }

}