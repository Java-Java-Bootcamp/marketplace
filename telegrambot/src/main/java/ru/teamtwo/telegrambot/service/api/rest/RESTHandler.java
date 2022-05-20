package ru.teamtwo.telegrambot.service.api.rest;

import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.product.ProductSearchHandler;

import java.util.Set;

public interface RESTHandler {
    CustomerState getCustomerState(Long userId) throws RESTHandlerException;
    void saveCustomerState(CustomerState customerState);

    Set<ProductDto> queryProducts(ProductSearchHandler.ProductQuery productQuery);
}
