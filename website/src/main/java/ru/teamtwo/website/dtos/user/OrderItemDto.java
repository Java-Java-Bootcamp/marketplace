package ru.teamtwo.website.dtos.user;

import lombok.Data;
import ru.teamtwo.website.model.user.OrderItem;

import java.io.Serializable;

@Data
public class OrderItemDto implements Serializable {
    private final Integer id;
    private final Integer productId;
    private final Integer quantity;
    public OrderItemDto(OrderItem entity){
        this.id = entity.getId();
        this.productId = entity.getProduct().getId();
        this.quantity = entity.getQuantity();
    }
}
