package ru.teamtwo.telegrambot.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    int id;
    String name;
    String manufacturer;
    String description;
    String category;
    double rating;
    double price;
    String sellerName;
    int sellerRating;
    int available;
}
