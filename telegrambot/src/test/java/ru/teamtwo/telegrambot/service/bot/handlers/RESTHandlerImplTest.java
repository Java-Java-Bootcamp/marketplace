package ru.teamtwo.telegrambot.service.bot.handlers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.client.customer.CustomerClient;
import ru.teamtwo.telegrambot.client.product.ProductOfferClient;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.teamtwo.telegrambot.model.sorting.SortingTypeAscDesc.ASC;
import static ru.teamtwo.telegrambot.model.sorting.SortingTypeField.PRODUCT_RATING;

class RESTHandlerImplTest {

    static final Long USER_ID = 12345L;

    @Mock
    User user;
    Map<Integer, Integer> testCart = new HashMap<>();
    Set<ProductDto> testQueryResult = new HashSet<>();
    CustomerState filledCustomerState;
    @Mock
    CustomerClient customerClient;
    @Mock
    ProductOfferClient customerClient;
    RESTHandler restHandler = new RESTHandlerImpl();

    @BeforeEach
    void setUp() {
        testCart.put(123, 321);
        Mockito.when(user.getId()).thenReturn(USER_ID);

        filledCustomerState = CustomerState.builder()
                .address("Address")
                .user(user)
                .chatId("12345")
                .state(CustomerState.State.WAITING_FOR_QUANTITY)
                .searchQuery("Search query")
                .sortingTypeField(PRODUCT_RATING)
                .sortingTypeAscDesc(ASC)
                .offset(5)
                .limit(10)
                .cart(testCart)
                .currentProductId(123)
                .queryResult(testQueryResult)
                .build();
    }

    @Test
    void getCustomerState() {
        Mockito.when(customerClient.get(user.getId())).thenReturn(filledCustomerState);

        CustomerState customerState = restHandler.getCustomerState(USER_ID);

        Mockito.verify(customerClient, Mockito.times(1)).get(user.getId());
        Assertions.assertThat(filledCustomerState);
    }

    @Test
    void saveCustomerState() {
        restHandler.saveCustomerState(filledCustomerState);

        Mockito.verify(customerClient, Mockito.times(1)).post();
    }

    @Test
    void queryProducts() {
        ProductQuery productQuery = new ProductQuery("query");

        List<ProductDto> productQueryResult = restHandler.queryProducts(productQuery);

        Mockito.verify(customerClient, Mockito.times(1)).post();
    }
}