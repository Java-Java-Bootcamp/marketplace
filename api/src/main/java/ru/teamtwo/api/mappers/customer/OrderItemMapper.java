package ru.teamtwo.api.mappers.customer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {
    @Autowired
    private ProductOfferRepository productOfferRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Mappings({
            @Mapping(target="orderId", source="orderItem.order.id"),
            @Mapping(target="productOfferId", source="orderItem.productOffer.id")
    })
    public abstract OrderItemDto convert(OrderItem orderItem);
    public abstract OrderItem convert(OrderItemDto orderItemDto);
    @AfterMapping
    protected void afterMapping(OrderItemDto orderItemDto, @MappingTarget OrderItem.OrderItemBuilder orderItemBuilder) {
        orderItemBuilder.order(orderRepository.getById(orderItemDto.orderId()));
        orderItemBuilder.productOffer(productOfferRepository.getById(orderItemDto.productOfferId()));
    }
}
