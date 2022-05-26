package ru.teamtwo.telegrambot.mapper;

import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.Set;

@Mapper(componentModel = "spring", imports = ProductOfferController.class)
public interface CustomerStateMapper {
    CustomerDto convert(CustomerState customerState);
    CustomerState convert(ResponseEntity<CustomerDto> customerDto, Set<CartItemDto> cartItemDtos, Set<CustomerOrder> customerOrders);
}
