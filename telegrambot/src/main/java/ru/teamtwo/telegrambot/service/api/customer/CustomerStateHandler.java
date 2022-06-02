package ru.teamtwo.telegrambot.service.api.customer;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;

public interface CustomerStateHandler {
    CustomerState get(User user) throws RESTHandlerException;
    void save(CustomerState customerState) throws RESTHandlerException;
}
