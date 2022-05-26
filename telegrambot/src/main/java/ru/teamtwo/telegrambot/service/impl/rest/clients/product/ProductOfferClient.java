package ru.teamtwo.telegrambot.service.impl.rest.clients.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import java.util.Set;

@FeignClient(url = "${telegrambot.rest.webClientUri}product_offer", name="productOffer")
public interface ProductOfferClient extends ProductOfferController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<ProductOfferDto> get(@PathVariable Long id);

    @Override
    @ResponseBody
    @PostMapping("")
    ResponseEntity<Set<Long>> save(Set<ProductOfferDto> dto);

    @Override
    @GetMapping("{id}")
    ResponseEntity<Set<ProductOfferDto>> query(ProductQuery productQuery);
}
