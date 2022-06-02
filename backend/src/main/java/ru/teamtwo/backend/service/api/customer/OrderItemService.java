package ru.teamtwo.backend.service.api.customer;

import ru.teamtwo.backend.service.api.BaseService;
import ru.teamtwo.backend.service.api.GetByOrderService;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

public interface OrderItemService
        extends BaseService<OrderItemDto>, GetByOrderService<OrderItemDto> {
}
