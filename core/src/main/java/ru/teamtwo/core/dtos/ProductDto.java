package ru.teamtwo.core.dtos;

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
