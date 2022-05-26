package ru.teamtwo.api.mappers.customer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.core.dtos.customer.OrderDto;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    @Autowired
    private CustomerRepository customerRepository;
    @Mappings({
            @Mapping(target="customerId", source="customer.id")
    })
    public abstract OrderDto convert(Order order);
    public abstract Order convert(OrderDto orderDto);
    @AfterMapping
    protected void afterMapping(OrderDto orderDto, @MappingTarget Order.OrderBuilder orderBuilder) {
        orderBuilder.customer(customerRepository.getById(orderDto.customerId()));
    }
}
