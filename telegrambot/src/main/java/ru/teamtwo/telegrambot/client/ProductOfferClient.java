package ru.teamtwo.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.teamtwo.core.dtos.ProductDto;

@FeignClient(url = "${telegrambot.rest.webClientUri}/marketplace/api/product_offer", name="productOffer")
public interface ProductOfferClient {
    @GetMapping("{id}")
    ProductDto get(@PathVariable Integer id);
}
