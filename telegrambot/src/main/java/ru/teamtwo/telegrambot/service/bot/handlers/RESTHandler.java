package ru.teamtwo.telegrambot.service.bot.handlers;

import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.List;

public interface RESTHandler {
    CustomerState getCustomerState(Long userId);
    void saveCustomerState(CustomerState customerState);

    List<ProductDto> queryProducts(ProductQuery productQuery);
}
