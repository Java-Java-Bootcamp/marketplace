package ru.teamtwo.core.dtos.customer;

public record CustomerDto(
        Long id,
        String name,
        String address,
        String stage,
        String searchQuery,
        String sortingTypeField,
        String sortingTypeAscDesc,
        Integer offset,
        Integer limit,
        Long currentProductId
){}
