package ru.teamtwo.telegrambot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private Integer id;
    private Integer productId;
    private Integer quantity;
}
