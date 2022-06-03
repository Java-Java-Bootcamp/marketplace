package ru.teamtwo.core.dtos.customer;

public record OrderItemDto (
        Long id,
        Long orderId,
        Long productOfferId,
        Integer quantity
) {}
