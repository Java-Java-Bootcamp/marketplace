package ru.teamtwo.telegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.UserState;

import java.util.List;


@Component
@RequiredArgsConstructor
public class TelegramBotUpdateHandler {
    final UserStateHandler userStateHandler;
    final List<ContextHandler> handlers;

    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            User user = update.getMessage().getFrom();
            UserState userState = userStateHandler.get(user);

            ProcessingContext context = new ProcessingContext(
                    update,
                    message,
                    chatId,
                    user,
                    userState
            );

            for (ContextHandler handler : handlers) {
                if (handler.shouldRun(context)) {
                    handler.execute(context);
                    break;
                }
            }
        }
    }
}
