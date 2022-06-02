package ru.teamtwo.backend.service.api.product;

import ru.teamtwo.backend.service.api.BaseService;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import java.util.Set;

public interface ProductOfferService extends BaseService<ProductOfferDto> {
    Set<ProductOfferDto> query(ProductOfferController.ProductQuery productQuery);
}
