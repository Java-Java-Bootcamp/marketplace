package ru.teamtwo.telegrambot.service.api.rest;

import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.model.product.Product;

import java.util.Set;

public interface RESTHandler {
    CustomerState getCustomerState(Long userId) throws RESTHandlerException;
    void saveCustomerState(CustomerState customerState) throws RESTHandlerException;

    Set<Product> queryProducts(ProductOfferController.ProductQuery productQuery) throws RESTHandlerException;
}
