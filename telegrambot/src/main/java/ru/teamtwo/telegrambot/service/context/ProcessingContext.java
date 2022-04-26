package ru.teamtwo.telegrambot.service.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ProcessingContext {
    private final Update update;
    private final String message;
    private final String chatId;
    private final User user;
    private final CustomerState customerState;
    private final String dataCallbackType;
    private final  Map<String, String> dataCallbackArgs;
}
