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

    @Autowired
    Context context;

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

        {
//          Приветствие и отображение главного меню
            if (update.hasMessage() && update.getMessage().hasText()
                    && update.getMessage().getText().equals("/start")) {
                try {
                    execute(telegramBotUpdateHandler.sendGreetingMessage(update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

//          Переход в поисковое меню
            else if (update.hasMessage() && update.getMessage().hasText()
                    && update.getMessage().getText().equals("Поиск")) {
                try {
                    execute(telegramBotUpdateHandler.goToSearch(update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

//          Переход в главное меню
            else if (update.hasMessage() && update.getMessage().hasText()
                    && update.getMessage().getText().equals("Главное меню")) {
                try {
                    execute(telegramBotUpdateHandler.goToMain(update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

//          Передача запроса в поиск
            else if (update.hasMessage() && update.getMessage().hasText()
            && context.getBuyerChatContext(update).equals("search")) {
                SendMessage searchResultMessage = telegramBotUpdateHandler.searchProducts(update);
                try {
                    execute(searchResultMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

//          Изменение параметров сортировки

        }
    }
}
