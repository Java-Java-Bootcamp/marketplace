package ru.teamtwo.core.dtos.product;

public record ProductDto (
        Integer id,
        String name,
        String category,
        String model,
        String manufacturer,
        String description,
        Integer price,
        Integer rating
) {}
