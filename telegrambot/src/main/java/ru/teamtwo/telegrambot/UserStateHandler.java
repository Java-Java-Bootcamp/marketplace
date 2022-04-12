package ru.teamtwo.telegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStateHandler {

    private Map<User, UserState> map = new HashMap<>();

    /**
     * Возвращает UserState указанного пользователя,
     * инициализирует стандартный, если его нет.
     * @param user Пользователь
     * @return UserState пользователя
     */
    public UserState get(User user){
        map.putIfAbsent(user, new UserState());
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
