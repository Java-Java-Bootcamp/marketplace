package ru.teamtwo.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@Slf4j
public class RESTTests {

    @Autowired
    TelegramBotRESTHandler restHandler;

    @Test
    public void cartStateTest(){
        Map<Integer, Integer> cart = new HashMap<>();
        cart.put(1, 10);
        cart.put(2, 5);
        cart.put(3, 100);

        restHandler.saveCartState(1L, cart);

        Map<Integer, Integer> cartState = restHandler.getCartState(1L);

        cartState.forEach((key, value) ->
            log.debug("{}, {}", key, value)
        );

        cartState.forEach((key, value) ->
                log.debug("{}, {}", key, restHandler.getProductById(key))
        );
    }
}
