package ru.teamtwo.core.dtos.controller.customer;

import ru.teamtwo.core.dtos.controller.BaseController;
import ru.teamtwo.core.dtos.controller.GetByOrderController;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

public interface OrderItemController
        extends BaseController<OrderItemDto>, GetByOrderController<OrderItemDto> {
}
