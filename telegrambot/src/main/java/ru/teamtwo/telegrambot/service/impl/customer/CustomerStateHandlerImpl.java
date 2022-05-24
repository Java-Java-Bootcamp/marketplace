package ru.teamtwo.telegrambot.service.impl.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;

@Component
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class CustomerStateHandlerImpl implements CustomerStateHandler {
    private final RESTHandler restHandler;

    /**
     * Возвращает State пользователя, если нет локально, запрашивает у сервера
     * если сервер говорит, что его нет, создает новый.
     */
    public CustomerState get(User user) throws RESTHandlerException {
        return restHandler.getCustomerState(user.getId());
    }

    @Override
    public void save(CustomerState customerState) throws RESTHandlerException {
        restHandler.saveCustomerState(customerState);
    }
}
