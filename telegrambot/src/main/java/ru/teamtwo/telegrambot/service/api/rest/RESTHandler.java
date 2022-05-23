package ru.teamtwo.telegrambot.service.api.rest;

import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.Set;

public interface RESTHandler {
    CustomerState getCustomerState(Long userId) throws RESTHandlerException;
    void saveCustomerState(CustomerState customerState) throws RESTHandlerException;

    Set<ProductOfferDto> queryProducts(ProductOfferController.ProductQuery productQuery) throws RESTHandlerException;
}
