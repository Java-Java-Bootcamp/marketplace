package ru.teamtwo.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.website.model.ProductOffer;
import ru.teamtwo.website.repository.ProductOfferRepository;
import ru.teamtwo.website.service.EntityGenerator;

import javax.annotation.PostConstruct;

@RestController("Marketplace controller")
@RequestMapping("/marketplace/api")
public class MarketplaceController {
    private final EntityGenerator generator;
    private final ProductOfferRepository repository;
    @Autowired
    public MarketplaceController(EntityGenerator generator, ProductOfferRepository repository) {
        this.generator = generator;
        this.repository = repository;
    }

    @GetMapping("/product-offers")
    public Iterable<ProductOffer> getProductOffersByProductName(@RequestParam("text") String productNamePart) {
        return repository.getProductOffersByProductName(productNamePart);
    }
    @PostConstruct
    private void init(){
        generator.generateEntities(repository);
    }

}