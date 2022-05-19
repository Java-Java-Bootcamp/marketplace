package ru.teamtwo.telegrambot.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandler;

@Component
@RequiredArgsConstructor
public class CustomerStateHandlerImpl implements CustomerStateHandler{
    private final RESTHandler restHandler;

    /**
     * Возвращает State пользователя, если нет локально, запрашивает у сервера
     * если сервер говорит, что его нет, создает новый.
     */
    public CustomerState get(User user){
        return restHandler.getCustomerState(user.getId());
    }

    @Override
    public void save(CustomerState customerState) {
        restHandler.saveCustomerState(customerState);
    }
}
