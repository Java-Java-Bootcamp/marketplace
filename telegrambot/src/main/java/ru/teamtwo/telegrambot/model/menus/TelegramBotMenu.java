package ru.teamtwo.telegrambot.model.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;

public class TelegramBotMenu extends ReplyKeyboardMarkup {
    /**
     * Добавляет в меню строку с несколькими ячейками
     * @param rowText тексты ячеек
     */
    public void addRow(String... rowText){
        KeyboardRow row = new KeyboardRow();
        row.addAll(Arrays.asList(rowText));
        this.getKeyboard().add(row);
    }

    /**
     * Добавляет в меню строку с одной ячейкой
     * @param rowText текст ячейки
     */
    public void addRow(String rowText){
        KeyboardRow row = new KeyboardRow();
        row.add(rowText);
        this.getKeyboard().add(row);
    }

    public TelegramBotMenu() {
        this.setKeyboard(new ArrayList<>());
        this.setResizeKeyboard(true);
    }
}