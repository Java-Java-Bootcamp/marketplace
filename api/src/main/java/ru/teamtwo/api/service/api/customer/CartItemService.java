package ru.teamtwo.api.service.api.customer;

import ru.teamtwo.api.service.api.BaseService;
import ru.teamtwo.api.service.api.GetByCustomerService;
import ru.teamtwo.core.dtos.customer.CartItemDto;

public interface CartItemService
        extends BaseService<CartItemDto>, GetByCustomerService<CartItemDto> {
}
