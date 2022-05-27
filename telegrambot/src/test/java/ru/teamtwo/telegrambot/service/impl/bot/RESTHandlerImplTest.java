package ru.teamtwo.telegrambot.service.impl.bot;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.telegrambot.CustomerStateTestUtils;
import ru.teamtwo.telegrambot.mapper.CustomerStateMapper;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CustomerClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductOfferClient;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class RESTHandlerImplTest {
    static final Long USER_ID = 12345L;

    @Mock
    Set<CartItemDto> testCart = new HashSet<>();
    Set<ProductDto> testQueryResult = new HashSet<>();
    @Autowired
    CustomerStateMapper customerStateMapper;
    CustomerState filledCustomerState;
    CustomerDto customerDto;
    @Mock
    CustomerClient customerClient;
    @Mock
    ProductOfferClient productOfferClient;
    @Autowired
    RESTHandler restHandler;

    @BeforeEach
    void setUp() {
        testCart.add(new CartItemDto(1L, 1L, 1L, 1));

        filledCustomerState = CustomerStateTestUtils.getCustomerState();
        customerDto = CustomerStateTestUtils.getCustomerDto();
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

        Set<CustomerDto> argThat = (Set<CustomerDto>) MockitoHamcrest.argThat(Matchers.hasItem(customerDto));
        Mockito.verify(customerClient, Mockito.times(1)).save(argThat);
    }

    @Test
    void queryProducts() throws RESTHandlerException {
        ProductOfferController.ProductQuery productQuery = new ProductOfferController.ProductQuery("query", ProductOfferController.SortingTypeField.PRODUCT_RATING, ProductOfferController.SortingTypeAscDesc.ASC, 0, 5);

        Set<ProductOfferDto> productQueryResult = restHandler.queryProducts(productQuery);

        Mockito.verify(productOfferClient, Mockito.times(1)).query(productQuery);
    }

}