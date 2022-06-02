package ru.teamtwo.telegrambot.service.api.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    void handle(Update update);
}
