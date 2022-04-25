package ru.teamtwo.core.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.core.models.user.OrderItem;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto implements Serializable {
    private Integer id;
    private Integer orderId;
    private Integer productOfferId;
    private Integer quantity;

    public OrderItemDto(OrderItem entity) {
        this.id = entity.getId();
        this.orderId = entity.getOrder().getId();
        this.productOfferId = entity.getProductOffer().getId();
        this.quantity = entity.getQuantity();
    }
}
