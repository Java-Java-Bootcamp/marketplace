package ru.teamtwo.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.core.dtos.user.CustomerDto;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class RESTTests {

    @Autowired
    TelegramBotRESTHandler restHandler;

    @Test
    public void cartStateTest(){
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(12345L);
        Mockito.when(user.getUserName()).thenReturn("user12345");

        UserState userState = new UserState();
        userState.setUser(user);
        userState.setAddress("address 12345");

        restHandler.updateCustomerInfo(userState);

        CustomerDto customerInfo = restHandler.getCustomerInfo(userState).orElseThrow();

        Map<Integer, Integer> cart = new HashMap<>();
        cart.put(1, 10);
        cart.put(2, 5);
        cart.put(3, 100);
        userState.setCart(cart);

        restHandler.saveCartState(userState);

        Map<Integer, Integer> cartState = restHandler.getCartState(1L);

        cartState.forEach((key, value) ->
            log.debug("{}, {}", key, value)
        );

        cartState.forEach((key, value) ->
                log.debug("{}, {}", key, restHandler.getProductById(key))
        );

        restHandler.postNewOrderFromUserCart(userState);
    }
}
