package ru.teamtwo.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.teamtwo.core.dtos.ProductDTO;

import java.util.List;

@FeignClient(url = "localhost:8081/marketplace/api/product-offers", name="productOffers")
public interface MarketplaceController {
    @GetMapping("")
    public List<ProductDTO> getProductOffersByProductName(@RequestParam(value = "filter", required = true) String filter,
                                                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                            @RequestParam(value = "limit", defaultValue = "20") int limit,
                                                            @RequestParam(value = "order", defaultValue = "desc_price") String order);
}