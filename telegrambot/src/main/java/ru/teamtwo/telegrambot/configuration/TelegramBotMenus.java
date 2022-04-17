package ru.teamtwo.telegrambot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.teamtwo.telegrambot.model.menus.TelegramBotMenu;

@Configuration
public class TelegramBotMenus {

    @Bean
    public static TelegramBotMenu getSearchMenu(){
        TelegramBotMenu keyboard = new TelegramBotMenu();
        keyboard.addRow("Главное меню");

        return keyboard;
    }

    @Bean
    public static TelegramBotMenu getMainMenuKeyboard(){
        TelegramBotMenu keyboard = new TelegramBotMenu();
        keyboard.addRow("Поиск");

        return keyboard;
    }

    /**
     * Возвращает меню для сортировки по полю товара: цена, рейтинг и т.д.
     * @return Меню
     */
    @Bean
    public static TelegramBotMenu getSortByFieldOffsetKeyboard(){
        TelegramBotMenu sortFieldKeyboard = new TelegramBotMenu();
        sortFieldKeyboard.addRow("Цена");
        sortFieldKeyboard.addRow("Рейтинг товара");
        sortFieldKeyboard.addRow("Рейтинг продавца");
        sortFieldKeyboard.addRow("Показать ещё");

        return sortFieldKeyboard;
    }

    /**
     * Возвращает меню для сортировки по убыванию/возрастанию
     * @return Меню
     */
    @Bean
    public static TelegramBotMenu getSortByAscDescOffsetKeyboard(){
        TelegramBotMenu sortAscDescKeyboard = new TelegramBotMenu();
        sortAscDescKeyboard.addRow("По возрастанию");
        sortAscDescKeyboard.addRow("По убыванию");
        sortAscDescKeyboard.addRow("Показать ещё");

        return sortAscDescKeyboard;
    }
}
