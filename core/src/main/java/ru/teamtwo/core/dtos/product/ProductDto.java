package ru.teamtwo.core.dtos.product;

public record ProductDto (
        Long id,
        String name,
        String category,
        String model,
        String manufacturer,
        String description,
        Integer price,
        Integer rating
) {}
