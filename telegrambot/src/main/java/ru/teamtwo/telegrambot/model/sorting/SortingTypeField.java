package ru.teamtwo.telegrambot.model.sorting;

/**
 * Виды сортировки по полям товара.
 */
public enum SortingTypeField {
    PRODUCT_NAME("Название"),
    PRODUCT_PRICE("Цена"),
    PRODUCT_RATING("Рейтинг"),
    SELLER_RATING("Рейтинг продавца");

    public final String inputName;

    SortingTypeField(String inputName) {
        this.inputName = inputName;
    }
}
