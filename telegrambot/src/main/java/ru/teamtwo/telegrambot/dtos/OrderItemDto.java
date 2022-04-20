package ru.teamtwo.telegrambot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto implements Serializable {
    private Integer id;
    private Integer orderId;
    private Integer productOfferId;
    private Integer quantity;
}
