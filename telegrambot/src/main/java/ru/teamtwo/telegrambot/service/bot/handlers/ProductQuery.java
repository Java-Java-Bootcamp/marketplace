package ru.teamtwo.telegrambot.service.bot.handlers;

import ru.teamtwo.telegrambot.model.sorting.SortingTypeAscDesc;
import ru.teamtwo.telegrambot.model.sorting.SortingTypeField;

public record ProductQuery(
    String query,
    SortingTypeField sortingTypeField,
    SortingTypeAscDesc sortingTypeAscDesc,
    Integer offset,
    Integer limit
) {}
