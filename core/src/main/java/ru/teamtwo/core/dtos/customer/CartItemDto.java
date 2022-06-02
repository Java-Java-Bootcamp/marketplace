package ru.teamtwo.core.dtos.customer;

public record CartItemDto(
        Long id,
        Long customerId,
        Long productOfferId,
        Integer quantity
){}
