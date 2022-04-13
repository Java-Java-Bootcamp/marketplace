package ru.teamtwo.website;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.teamtwo.website.model.Product;
import ru.teamtwo.website.model.ProductOffer;
import ru.teamtwo.website.model.Store;
import ru.teamtwo.website.repository.ProductOfferRepository;
import ru.teamtwo.website.repository.ProductRepository;
import ru.teamtwo.website.repository.StoreRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DataBaseInitializator implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final ProductOfferRepository productOfferRepository;

    @Override
    public void run(String... args) throws Exception {
        Product product1 = Product.builder()
                .category("food")
                .name("hamburger")
                .model("vegan")
                .price(BigDecimal.valueOf(100.20D))
                .manufacturer("McDonalds")
                .description("Cheap and tasty")
                .rate(4.05F)
                .build();
        Product product2 = Product.builder()
                .category("food2")
                .name("hamburger2")
                .model("vegan2")
                .price(BigDecimal.valueOf(100.20D))
                .manufacturer("McDonalds2")
                .description("Cheap and tasty2")
                .rate(4.05F)
                .build();

        Store store1 = Store.builder()
                .name("Самокат")
                .rate(4.0F)
                .build();
        Store store2 = Store.builder()
                .name("Самокат2")
                .rate(4.0F)
                .build();

        productRepository.save(product1);
        storeRepository.save(store1);
        productRepository.save(product2);
        storeRepository.save(store2);


        ProductOffer productOffer1 = ProductOffer.builder()
                .product(product1)
                .store(store1)
                .available(20L)
                .build();
        productOfferRepository.save(productOffer1);

        ProductOffer productOffer2 = ProductOffer.builder()
                .product(product1)
                .store(store2)
                .available(20L)
                .build();
        productOfferRepository.save(productOffer2);


    }

}
