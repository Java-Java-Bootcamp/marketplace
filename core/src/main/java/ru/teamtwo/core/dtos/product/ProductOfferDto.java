package ru.teamtwo.core.dtos.product;

public record ProductOfferDto (
        Long id,
        Long productId,
        Long storeId,
        Integer quantity
) {}
