package ru.teamtwo.telegrambot.service.customer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomerStateHandler {

    private Map<User, CustomerState> map = new HashMap<>();
    private final RESTHandler restHandler;

    public CustomerStateHandler(RESTHandler restHandler) {
        this.restHandler = restHandler;
    }

    /**
     * Возвращает State пользователя, если нет локально, запрашивает у сервера
     * если сервер говорит, что его нет, создает новый.
     */
    public CustomerState get(User user){
        if(!map.containsKey(user)){
            CustomerState customerState = new CustomerState();
            customerState.setUser(user);
            map.put(user, customerState);

            Optional<CustomerDto> customerDto = restHandler.getCustomerDTO(customerState);
            if(customerDto.isPresent()) {
                restHandler.updateCustomerCartFromServer(customerState);
                customerState.setAddress(customerDto.get().getAddress());
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
