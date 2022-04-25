package ru.teamtwo.core.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.core.models.user.CartItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private Integer id;
    private Integer customer;
    private Integer productId;
    private Integer quantity;

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.customer = cartItem.getCustomer().getId();
        this.productId = cartItem.getProduct().getId();
        this.quantity = cartItem.getQuantity();
        List<CartItemDto> collect = new Vector<>();
        collect.stream().max(Comparator.comparing(CartItemDto::hashCode));
    }
}
