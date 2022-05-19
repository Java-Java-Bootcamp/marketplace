package ru.teamtwo.telegrambot.service.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    void handle(Update update);
}
