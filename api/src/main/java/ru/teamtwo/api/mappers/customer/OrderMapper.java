package ru.teamtwo.api.mappers.customer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.api.models.customer.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
            @Mapping(target="customerId", source="orderItem.customer.id")
    })
    OrderDto convert(Order order);
    Order convert(OrderDto orderDto);
}
