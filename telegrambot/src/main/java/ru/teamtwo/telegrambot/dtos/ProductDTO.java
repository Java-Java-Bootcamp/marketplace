package ru.teamtwo.telegrambot.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    int id;

    String name;
    double price;
    double rating;

    String sellerName;
    int sellerRating;
    int sellerAmountLeft;
}
