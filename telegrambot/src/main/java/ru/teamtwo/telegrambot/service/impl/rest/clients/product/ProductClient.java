package ru.teamtwo.telegrambot.service.impl.rest.clients.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.teamtwo.core.dtos.controller.product.ProductController;
import ru.teamtwo.core.dtos.product.ProductDto;

import java.util.Set;

@FeignClient(url = "${telegrambot.rest.webClientUri}product", name="product")
public interface ProductClient extends ProductController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<ProductDto> get(@PathVariable Long id);

    @Override
    @PostMapping("")
    ResponseEntity<Set<Long>> save(@RequestBody Set<ProductDto> dto);
}
