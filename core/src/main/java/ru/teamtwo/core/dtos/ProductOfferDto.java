package ru.teamtwo.core.dtos;

public record ProductOfferDto (
    Integer id,
    Integer productId,
    Integer storeId,
    Integer quantity
) {}
