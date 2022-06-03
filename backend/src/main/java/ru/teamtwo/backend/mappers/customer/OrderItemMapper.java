package ru.teamtwo.backend.mappers.customer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamtwo.backend.mappers.BaseMapper;
import ru.teamtwo.backend.models.customer.OrderItem;
import ru.teamtwo.backend.repository.customer.OrderRepository;
import ru.teamtwo.backend.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper implements BaseMapper<OrderItem, OrderItemDto> {
    @Autowired
    private ProductOfferRepository productOfferRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Mappings({
            @Mapping(target="orderId", source="orderItem.order.id"),
            @Mapping(target="productOfferId", source="orderItem.productOffer.id")
    })
    public abstract OrderItemDto convertToDto(OrderItem orderItem);
    public abstract OrderItem convertToEntity(OrderItemDto orderItemDto);
    @AfterMapping
    protected void afterMapping(OrderItemDto orderItemDto, @MappingTarget OrderItem.OrderItemBuilder orderItemBuilder) {
        orderItemBuilder.order(orderRepository.getById(orderItemDto.orderId()));
        orderItemBuilder.productOffer(productOfferRepository.getById(orderItemDto.productOfferId()));
    }
}
