package ru.teamtwo.api.controller.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.product.ProductDto;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/product_offer")
public class ProductOfferController {

    private final ProductOfferRepository repository;

    public ProductOfferController(ProductOfferRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    public ProductDto get(@PathVariable Integer id) {
        try {
            return new ProductDto(repository.getProductOfferById(id));
        } catch (Exception e) {
            throw new ItemNotFoundException("Can't get product offer " + id);
        }
    }
}
