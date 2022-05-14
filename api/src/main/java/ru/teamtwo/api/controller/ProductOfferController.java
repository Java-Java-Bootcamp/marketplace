package ru.teamtwo.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.core.dtos.ProductDTO;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.repository.ProductOfferRepository;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/product_offer")
public class ProductOfferController {

    @Autowired
    private ProductOfferRepository repository;


    @GetMapping("{id}")
    public ProductDTO get(@PathVariable Integer id) {
        try {
            return new ProductDTO(repository.getProductOfferById(id));
        } catch (Exception e) {
            throw new ItemNotFoundException("Can't get product offer " + id);
        }
    }
}