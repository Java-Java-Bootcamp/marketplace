package ru.teamtwo.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.website.model.ProductOffer;
import ru.teamtwo.website.repository.ProductOfferRepository;

import java.util.List;

@RestController("Marketplace controller")
@RequestMapping("/marketplace/api")
public class MarketplaceController {
    @Autowired
    private ProductOfferRepository repository;

    @GetMapping("/product-offers")
    public List<ProductOffer> getProductOffersByProductName(@RequestParam("text") String productNamePart) {
        return repository.getProductOffersByProductName("%" + productNamePart + "%");
    }

}
