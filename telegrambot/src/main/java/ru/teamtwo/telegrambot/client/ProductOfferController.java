package ru.teamtwo.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.teamtwo.core.dtos.ProductDTO;

@FeignClient(url = "localhost:8081/marketplace/api/product_offer", name="productOffer")
public interface ProductOfferController {
    @GetMapping("{id}")
    public ProductDTO get(@PathVariable Integer id);
}
