package ru.teamtwo.core.dtos.customer;

public record CustomerDto(
    Long userId,
    String name,
    String address,
    String chatId,
    String state,
    String searchQuery,
    String sortingTypeField,
    String sortingTypeAscDesc,
    Integer offset,
    Integer limit,
    Integer currentProductId
){}
