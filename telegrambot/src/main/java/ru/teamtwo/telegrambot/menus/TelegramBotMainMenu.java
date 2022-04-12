package ru.teamtwo.telegrambot.menus;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBotMainMenu {

    public SendMessage mainMenu(Update update) {
        SendMessage message = new SendMessage();
        TelegramBotMenu mainMenuKeyboard = new TelegramBotMenu();
        mainMenuKeyboard.addRow("Поиск");
        message.setText("Для поиска товара нажмите Поиск");
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyMarkup(mainMenuKeyboard);
        return message;
        }
    }
