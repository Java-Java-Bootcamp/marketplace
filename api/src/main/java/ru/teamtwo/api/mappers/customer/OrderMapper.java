package ru.teamtwo.api.mappers.customer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.core.dtos.customer.OrderDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
            @Mapping(target="customerId", source="customer.id")
    })
    OrderDto convert(Order order);
    Order convert(OrderDto orderDto);
}
