package ru.teamtwo.api.mappers.customer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.api.models.customer.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mappings({
            @Mapping(target="orderId", source="orderItem.order.id"),
            @Mapping(target="productOfferId", source="orderItem.productOffer.id")
    })
    OrderItemDto convert(OrderItem orderItem);

    OrderItem convert(OrderItemDto orderItemDto);
}
