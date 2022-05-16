package ru.teamtwo.core.dtos.product;

public record ProductOfferDto (
    Integer id,
    Integer productId,
    Integer storeId,
    Integer quantity
) {}
