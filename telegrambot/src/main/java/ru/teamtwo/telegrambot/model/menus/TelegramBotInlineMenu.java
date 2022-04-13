package ru.teamtwo.telegrambot.model.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;

/**
 * Extension для меню, отображающегося под постом. Упрощает создание.
 */
public class TelegramBotInlineMenu extends InlineKeyboardMarkup {

    /**
     * Создает менюшку с одной кнопкой, собственно
     * @param text Текст на кнопке
     * @param callbackData CallbackData для update.getCallbackQuery().getData()
     * @return Меню для setReplyMarkup
     */
    public static TelegramBotInlineMenu createMenuWithOneButton(String text, String callbackData){
        TelegramBotInlineMenu menu = new TelegramBotInlineMenu();
        menu.addButton(text,callbackData);
        return menu;
    }

    /**
     * Создает меню и инициалиризует листы со строчками
     */
    public TelegramBotInlineMenu(){
        this.setKeyboard(new ArrayList<>());
        this.getKeyboard().add(new ArrayList<>());
    }

    /**
     * Добавляет кнопку в последнюю добавленную строку
     * @param text Текст кнопки
     * @param callbackData CallbackData для update.getCallbackQuery().getData()
     */
    public void addButton(String text, String callbackData){
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        this.getKeyboard().get(this.getKeyboard().size()-1).add(inlineKeyboardButton);
    }

    /**
     * Добавляет строку в менюшку
     */
    public void addRow(){
        this.getKeyboard().add(new ArrayList<>());
    }
}
