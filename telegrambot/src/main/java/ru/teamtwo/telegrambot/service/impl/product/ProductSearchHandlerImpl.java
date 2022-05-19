package ru.teamtwo.telegrambot.service.impl.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.service.api.product.ProductSearchHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProductSearchHandlerImpl implements ProductSearchHandler {
    final RESTHandler restHandler;
    @Override
    public Set<ProductDto> search(ProductQuery productQuery) {
        return restHandler.queryProducts(productQuery);
    }
}
