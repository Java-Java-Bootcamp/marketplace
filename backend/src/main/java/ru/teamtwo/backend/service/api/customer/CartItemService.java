package ru.teamtwo.backend.service.api.customer;

import ru.teamtwo.backend.service.api.BaseService;
import ru.teamtwo.backend.service.api.GetByCustomerService;
import ru.teamtwo.core.dtos.customer.CartItemDto;

public interface CartItemService
        extends BaseService<CartItemDto>, GetByCustomerService<CartItemDto> {
}
