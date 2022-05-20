package ru.teamtwo.core.dtos.controller.customer;

import ru.teamtwo.core.dtos.controller.BaseController;
import ru.teamtwo.core.dtos.controller.GetByCustomerController;
import ru.teamtwo.core.dtos.customer.OrderDto;

public interface OrderController
        extends BaseController<OrderDto>, GetByCustomerController<OrderDto> {
}

