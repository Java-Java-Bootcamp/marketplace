package ru.teamtwo.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.repository.ProductOfferRepository;
import ru.teamtwo.core.dtos.ProductDTO;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/product-offers")
public class MarketplaceController {
    private final ProductOfferRepository repository;

    public MarketplaceController(ProductOfferRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<ProductDTO> getProductOffersByProductName(@RequestParam(value = "filter", required = true) String filter,
                                                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                            @RequestParam(value = "limit", defaultValue = "20") int limit,
                                                            @RequestParam(value = "order", defaultValue = "desc_price") String order) {
        log.info("MarketPlaceController /product-offers @GET ({},{},{},{})",filter,offset,limit,order);

        Sort.Direction sortType = mapParamToDirection(order);
        String sortType2 = mapParamToOrderField(order);

        log.info("directions: {}, {}", sortType, sortType2);

        final PageRequest pageRequest = PageRequest.of(offset, limit)
                .withSort(mapParamToDirection(order), mapParamToOrderField(order));
        return repository.getProductOffersByProductName(filter, pageRequest)
                .getContent()
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    private Sort.Direction mapParamToDirection(String order) {
        int index = order.indexOf("_");
        if (index > -1) {
            String direction = order.substring(0, index);
            return Sort.Direction.fromString(direction);
        } else {
            return Sort.Direction.ASC;
        }
    }

    private String mapParamToOrderField(String order) {
        int index = order.indexOf("_");
        if (index > -1) {
            return order.substring(index + 1).toLowerCase(Locale.ROOT);
        } else {
            return order.toLowerCase(Locale.ROOT);
        }
    }
}