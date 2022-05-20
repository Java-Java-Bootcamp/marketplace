package ru.teamtwo.api.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace/api/product_offer")
public class ProductOfferControllerImpl implements ProductOfferController {
    private final ProductOfferRepository repository;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<ProductOfferDto> get(@PathVariable Long id) {
        try {
            return new ProductDto(repository.getProductOfferById(id));
        } catch (Exception e) {
            throw new ItemNotFoundException("Can't get product offer " + id);
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Integer> save(ProductOfferDto dto) {
        return null;
    }

    @Override
    @GetMapping("query/")
    public Set<ProductOfferDto> query(ProductQuery productQuery) {
        return null;
    }
}
