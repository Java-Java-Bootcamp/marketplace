package ru.teamtwo.telegrambot.model.product;

import ru.teamtwo.core.dtos.product.ProductOfferDto;

public record Product (
    ProductOfferDto productOfferDto,
    String name,
    Integer price,
    Integer rating,
    String seller,
    Integer sellerRating,
    Integer quantity
    ){}
