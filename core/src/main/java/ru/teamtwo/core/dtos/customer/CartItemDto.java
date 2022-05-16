package ru.teamtwo.core.dtos.customer;

public record CartItemDto(
    Integer id,
    Integer customerId,
    Integer productId,
    Integer quantity
){}
