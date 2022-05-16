package ru.teamtwo.core.dtos.customer;

public record OrderItemDto (
    Integer id,
    Integer orderId,
    Integer productOfferId,
    Integer quantity
) {}
