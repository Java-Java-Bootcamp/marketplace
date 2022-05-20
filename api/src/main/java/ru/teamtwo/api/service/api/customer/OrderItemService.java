package ru.teamtwo.api.service.api.customer;

import ru.teamtwo.api.service.api.BaseService;
import ru.teamtwo.api.service.api.GetByOrderService;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

public interface OrderItemService
        extends BaseService<OrderItemDto>, GetByOrderService<OrderItemDto> {
}
