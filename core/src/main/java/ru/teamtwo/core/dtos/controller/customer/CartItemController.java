package ru.teamtwo.core.dtos.controller.customer;

import ru.teamtwo.core.dtos.controller.BaseController;
import ru.teamtwo.core.dtos.controller.GetByCustomerController;
import ru.teamtwo.core.dtos.customer.CartItemDto;

public interface CartItemController
        extends BaseController<CartItemDto>, GetByCustomerController<CartItemDto> {
}
