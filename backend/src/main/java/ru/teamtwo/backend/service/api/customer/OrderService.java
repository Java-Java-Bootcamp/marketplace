package ru.teamtwo.backend.service.api.customer;

import ru.teamtwo.backend.service.api.BaseService;
import ru.teamtwo.backend.service.api.GetByCustomerService;
import ru.teamtwo.core.dtos.customer.OrderDto;

public interface OrderService
        extends BaseService<OrderDto>, GetByCustomerService<OrderDto> {
}

