package ru.teamtwo.telegrambot.service;

/**
 * Виды сортировки - по убывающей/возрастающей.
 */
public enum SortingTypeAscDesc {
    ASC("По возрастанию"),
    DESC("По убыванию");

    public final String inputName;

    SortingTypeAscDesc(String inputName) {
        this.inputName = inputName;
    }
}
