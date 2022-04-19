package ru.teamtwo.website.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.website.model.user.CartItem;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private Integer id;
    private Integer customer;
    private Integer productId;
    private Integer quantity;

    public CartItemDto(CartItem cartItem){
        this.id = cartItem.getId();
        this.customer = cartItem.getCustomer().getId();
        this.productId = cartItem.getProduct().getId();
        this.quantity = cartItem.getQuantity();
    }
}
