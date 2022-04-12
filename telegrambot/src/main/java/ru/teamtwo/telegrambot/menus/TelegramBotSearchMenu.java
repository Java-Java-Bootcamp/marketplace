package ru.teamtwo.telegrambot.menus;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBotSearchMenu {

    public SendMessage searchMenu(Update update) {
        TelegramBotMenu searchKeyboard = new TelegramBotMenu();
        SendMessage message = new SendMessage();
        searchKeyboard.addRow("Главное меню");
        message.setText("Введите поисковой запрос");
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyMarkup(searchKeyboard);
        return message;
    }
}
