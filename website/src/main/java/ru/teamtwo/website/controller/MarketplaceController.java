package ru.teamtwo.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.website.dtos.ProductDTO;
import ru.teamtwo.website.model.ProductOffer;
import ru.teamtwo.website.repository.ProductOfferRepository;
import ru.teamtwo.website.service.EntityGenerator;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProductDTO> getProductOffersByProductName(@RequestParam(value = "filter", required = true) String filter,
                                                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                            @RequestParam(value = "limit", defaultValue = "20") int limit,
                                                            @RequestParam(value = "order", defaultValue = "desc_price") String order) {
        final PageRequest pageRequest = PageRequest.of(offset, limit);
        return repository.getProductOffersByProductName(productNamePart, order == null ? pageRequest : pageRequest.withSort(Sort.by(order)))
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @PostConstruct
    private void init() {
        generator.generateEntities(repository);
    }

}