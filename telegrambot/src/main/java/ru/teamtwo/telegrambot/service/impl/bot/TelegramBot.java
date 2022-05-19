package ru.teamtwo.telegrambot.service.impl.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamtwo.telegrambot.service.api.bot.UpdateHandler;


@Component
public class TelegramBot extends TelegramLongPollingBot implements ru.teamtwo.telegrambot.service.api.bot.TelegramBot {

    final UpdateHandler updateHandler;

    public TelegramBot(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

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
        updateHandler.handle(update);
    }
}
