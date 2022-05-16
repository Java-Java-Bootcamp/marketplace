package ru.teamtwo.core.dtos.customer;

import java.util.Set;

public record CartItemArrayDto (
    Set<CartItemDto> cartItemDtoList
){}
