package ru.teamtwo.backend.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.backend.service.api.product.ProductOfferService;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("product_offer")
public class ProductOfferControllerImpl implements ProductOfferController {
    private final ProductOfferService productOfferService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<ProductOfferDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(productOfferService.get(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Set<Long>> save(@RequestBody  Set<ProductOfferDto> productOfferDto) {
        return ResponseEntity.ok(productOfferService.save(productOfferDto));
    }

    @Override
    @PostMapping("query/")
    public ResponseEntity<Set<ProductOfferDto>> query(@RequestBody ProductQuery productQuery) {
        return ResponseEntity.ok(productOfferService.query(productQuery));
    }
}
