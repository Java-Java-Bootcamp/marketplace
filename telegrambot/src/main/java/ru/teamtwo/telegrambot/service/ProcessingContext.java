package ru.teamtwo.telegrambot.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.UserState;

@AllArgsConstructor
@Getter
public class ProcessingContext {
    private final Update update;
    private final String message;
    private final String chatId;
    private final User user;
    private final UserState userState;
}
