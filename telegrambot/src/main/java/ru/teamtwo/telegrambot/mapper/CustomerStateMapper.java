package ru.teamtwo.telegrambot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.Set;

@Mapper(componentModel = "spring", imports = ProductOfferController.class)
public interface CustomerStateMapper {
    @Mappings({
            @Mapping(target="id", source="customerState.userId"),
    })
    CustomerDto convert(CustomerState customerState);
    @Mappings({
            @Mapping(target="userId", source="customerDto.id"),
            @Mapping(target="cart", source="cartItemDtos"),
            @Mapping(target="orders", source="customerOrders"),
    })
    CustomerState convert(CustomerDto customerDto, Set<CartItemDto> cartItemDtos, Set<CustomerOrder> customerOrders);
}
