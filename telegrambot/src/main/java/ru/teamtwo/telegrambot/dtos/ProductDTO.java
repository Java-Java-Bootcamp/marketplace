package ru.teamtwo.telegrambot.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private String manufacturer;
    private String description;
    private String category;
    private double rating;
    private double price;
    private String sellerName;
    private int sellerRating;
    private int available;
}
