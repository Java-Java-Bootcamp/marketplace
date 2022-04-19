package ru.teamtwo.telegrambot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemArrayDto {
    private Set<CartItemDto> cartItemDtoList;
}
