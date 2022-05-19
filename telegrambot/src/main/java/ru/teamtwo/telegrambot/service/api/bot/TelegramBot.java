package ru.teamtwo.telegrambot.service.api.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramBot {
    void onUpdateReceived(Update update);
}
