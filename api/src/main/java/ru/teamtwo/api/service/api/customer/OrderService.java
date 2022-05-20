package ru.teamtwo.api.service.api.customer;

import ru.teamtwo.api.service.api.BaseService;
import ru.teamtwo.api.service.api.GetByCustomerService;
import ru.teamtwo.core.dtos.customer.OrderDto;

public interface OrderService
        extends BaseService<OrderDto>, GetByCustomerService<OrderDto> {
}

