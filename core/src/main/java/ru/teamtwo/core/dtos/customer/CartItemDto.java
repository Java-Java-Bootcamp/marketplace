package ru.teamtwo.core.dtos.customer;

public record CartItemDto(
    Integer id,
    Long customerId,
    Integer productId,
    Integer quantity
){}
