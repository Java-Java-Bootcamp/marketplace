package ru.teamtwo.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.teamtwo.core.dtos.ProductDTO;

@FeignClient(url = "localhost:8081/marketplace/api/product_offer", name="productOffer")
public interface ProductOfferClient {
    @GetMapping("{id}")
    ProductDTO get(@PathVariable Integer id);
}
