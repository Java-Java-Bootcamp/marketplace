package ru.teamtwo.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.models.customer.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
            @Mapping(target="customerId", source="orderItem.customer.id")
    })
    OrderDto convert(Order order);
    Order convert(OrderDto orderDto);
}
