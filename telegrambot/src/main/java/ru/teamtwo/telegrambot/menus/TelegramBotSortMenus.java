package ru.teamtwo.telegrambot.menus;

import org.springframework.context.annotation.Bean;

/**
 * Хранит в себе менюшки для сортировки товаров
 */
public class TelegramBotSortMenus {

    /**
     * Возвращает меню для сортировки по полю товара: цена, рейтинг и т.д.
     * @return Меню
     */
    @Bean
    public static TelegramBotMenu getSortByFieldKeyboard(){
        TelegramBotMenu sortFieldKeyboard = new TelegramBotMenu();
        sortFieldKeyboard.addRow("Цена");
        sortFieldKeyboard.addRow("Рейтинг товара");
        sortFieldKeyboard.addRow("Рейтинг продавца");

        return sortFieldKeyboard;
    }

    /**
     * Возвращает меню для сортировки по убыванию/возрастанию
     * @return Меню
     */
    @Bean
    public static TelegramBotMenu getSortByAscDescKeyboard(){
        TelegramBotMenu sortAscDescKeyboard = new TelegramBotMenu();
        sortAscDescKeyboard.addRow("По возрастанию");
        sortAscDescKeyboard.addRow("По убыванию");

        return sortAscDescKeyboard;
    }
}