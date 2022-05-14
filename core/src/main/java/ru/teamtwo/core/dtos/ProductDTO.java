package ru.teamtwo.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private String manufacturer;
    private String description;
    private String category;
    private double rating;
    private double price;
    private String sellerName;
    private double sellerRating;
    private long quantity;
}
