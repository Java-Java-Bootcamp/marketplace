package ru.teamtwo.telegrambot.model.sorting;

/**
 * Виды сортировки по полям товара.
 */
public enum SortingType {
    PRODUCT_NAME("Название"),
    PRODUCT_PRICE("Цена"),
    PRODUCT_RATING("Рейтинг"),
    SELLER_RATING("Рейтинг продавца");

    public final String inputName;

    SortingType(String inputName) {
        this.inputName = inputName;
    }
}
