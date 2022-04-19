package ru.teamtwo.website.dtos.user;

import lombok.Data;
import ru.teamtwo.website.model.user.CartItem;

import java.io.Serializable;

@Data
public class CartItemDto implements Serializable {
    private final Integer id;
    private final Integer productId;
    private final Integer quantity;

    public CartItemDto(CartItem cartItem){
        this.id = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.quantity = cartItem.getQuantity();
    }
}
