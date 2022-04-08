package ru.teamtwo.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        TelegramBotUpdateHandler updateHandler = new TelegramBotUpdateHandler();

        {
            if (update.hasMessage() && update.getMessage().hasText()
            && update.getMessage().getText().equals("/search")) {

//              Передача текста поиска в метод поиска товаров
                String reply = updateHandler.searchProducts(update);

//              формирование объекта с текстом ответа и id чата
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText(reply);

//              Отправка результата вызова метода поиска товаров пользователю
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
