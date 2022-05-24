package ru.teamtwo.api.mappers.customer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.core.dtos.customer.CartItemDto;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mappings({
            @Mapping(target="customerId", source="cartItem.customer.id"),
            @Mapping(target="productId", source="cartItem.product.id")
    })
    CartItemDto convert(CartItem cartItem);
    CartItem convert(CartItemDto cartItemDto);
}
