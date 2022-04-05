package ru.teamtwo.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    TelegramBotUpdateHandler telegramBotUpdateHandler;

    @Override
    public String getBotUsername() {
        return "Marketplace Bot";
    }

    @Override
    public String getBotToken() {
        return "5189238062:AAEAKaN6jnS223nSlwKu3QPsLKS0rXi3C6c";
    }

    @Override
    public void onUpdateReceived(Update update) {
        //TODO: call telegramBotUpdateHandler;
    }
}
