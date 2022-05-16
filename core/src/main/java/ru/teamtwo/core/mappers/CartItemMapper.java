package ru.teamtwo.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.models.customer.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mappings({
            @Mapping(target="customerId", source="cartItem.customer.id"),
            @Mapping(target="productId", source="cartItem.product.id")
    })
    CartItemDto convert(CartItem cartItem);

    CartItem convert(CartItemDto cartItemDto);
}
