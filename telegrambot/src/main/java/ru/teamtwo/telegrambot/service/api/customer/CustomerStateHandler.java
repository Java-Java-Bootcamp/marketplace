package ru.teamtwo.telegrambot.service.api.customer;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

public interface CustomerStateHandler {
    CustomerState get(User user);
    void save(CustomerState customerState);
}
