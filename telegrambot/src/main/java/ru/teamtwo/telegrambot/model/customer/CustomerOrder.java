package ru.teamtwo.telegrambot.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

import java.util.Set;

@Data
@AllArgsConstructor
public class CustomerOrder {
    OrderDto orderDto;
    Set<OrderItemDto> orderItemDtos;
}
