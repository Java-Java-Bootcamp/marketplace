package ru.teamtwo.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.core.dtos.user.CustomerDto;
import ru.teamtwo.telegrambot.model.UserState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserStateHandler {

    private Map<User, UserState> map = new HashMap<>();
    @Autowired
    private TelegramBotRESTHandler restHandler;

    /**
     * Возвращает UserState указанного пользователя,
     * инициализирует стандартный, если его нет.
     * @param user Пользователь
     * @return UserState пользователя
     */
    public UserState get(User user){
        if(!map.containsKey(user)){
            UserState userState = new UserState();
            userState.setUser(user);
            map.put(user, userState);

            Optional<CustomerDto> customerDto = restHandler.getCustomerInfo(userState);
            if(customerDto.isPresent()) {
                Map<Integer, Integer> cartState = restHandler.getCartState(user.getId());
                userState.setCart(cartState);
                userState.setAddress(customerDto.get().getAddress());
            }
        }
        return map.get(user);
    }

    /**
     * Сбрасывает UserState пользователя к дефолтным параметрам
     * @param user
     */
    public void resetUser(User user){
        map.get(user).reset();
    }
}
