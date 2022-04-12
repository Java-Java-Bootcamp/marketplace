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

    public ProductDTO(int id, String name, String manufacturer, String description, String category, double rating, double price, String sellerName, int sellerRating, int available) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.description = description;
        this.category = category;
        this.rating = rating;
        this.price = price;
        this.sellerName = sellerName;
        this.sellerRating = sellerRating;
        this.available = available;
    }
}