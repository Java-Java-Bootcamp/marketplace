package ru.teamtwo.core.dtos.customer;

public record CartItemDto(
        Long id,
        Long customerId,
        Long productId,
        Integer quantity
){}
