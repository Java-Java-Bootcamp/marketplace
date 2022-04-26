package ru.teamtwo.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandler;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class RESTTests {

    @Autowired
    RESTHandler restHandler;

    @Test
    public void cartStateTest(){
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(12345L);
        Mockito.when(user.getUserName()).thenReturn("user12345");

        CustomerState customerState = new CustomerState();
        customerState.setUser(user);
        customerState.setAddress("address 12345");

        restHandler.updateCustomerInfo(customerState);

        CustomerDto customerInfo = restHandler.getCustomerInfo(customerState).orElseThrow();

        Map<Integer, Integer> cart = new HashMap<>();
        cart.put(1, 10);
        cart.put(2, 5);
        cart.put(3, 100);
        customerState.setCart(cart);

        restHandler.saveCartState(customerState);

        restHandler.getCartState(customerState);

        customerState.getCart().forEach((key, value) ->
            log.debug("{}, {}", key, value)
        );

        customerState.getCart().forEach((key, value) ->
                log.debug("{}, {}", key, restHandler.getProductDTOById(key))
        );

        restHandler.postNewOrderFromUserCart(customerState);
    }
}
