package ru.teamtwo.telegrambot.service.api.product;

import ru.teamtwo.core.dtos.product.ProductDto;

import java.util.Set;

public interface ProductSearchHandler {
    Set<ProductDto> search(ProductQuery productQuery);



    /**
     * Виды сортировки - по убывающей/возрастающей.
     */

}
