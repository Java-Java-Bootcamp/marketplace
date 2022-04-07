package ru.teamtwo.telegrambot;

/**
 * Хранит в себе менюшки для сортировки товаров
 */
public class TelegramBotSortMenus {

    private static TelegramBotMenu sortFieldKeyboard;
    private static TelegramBotMenu sortAscDescKeyboard;

    /**
     * Возвращает меню для сортировки по полю товара: цена, рейтинг и т.д.
     * @return Меню
     */
    public static TelegramBotMenu getSortByFieldKeyboard(){
        if(sortFieldKeyboard==null){
            sortFieldKeyboard = new TelegramBotMenu();
            sortFieldKeyboard.addRow("Цена");
            sortFieldKeyboard.addRow("Рейтинг товара");
            sortFieldKeyboard.addRow("Рейтинг продавца");
        }
        return sortFieldKeyboard;
    }

    /**
     * Возвращает меню для сортировки по убыванию/возрастанию
     * @return Меню
     */
    public static TelegramBotMenu getSortByAscDescKeyboard(){
        if(sortAscDescKeyboard ==null) {
            sortAscDescKeyboard = new TelegramBotMenu();
            sortAscDescKeyboard.addRow("По возрастанию");
            sortAscDescKeyboard.addRow("По убыванию");
        }
        return sortAscDescKeyboard;
    }
}
