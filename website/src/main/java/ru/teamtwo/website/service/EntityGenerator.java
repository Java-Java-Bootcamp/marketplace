package ru.teamtwo.website.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.teamtwo.website.model.Product;
import ru.teamtwo.website.model.ProductOffer;
import ru.teamtwo.website.model.Store;
import ru.teamtwo.website.repository.ProductOfferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EntityGenerator {
    @Value("${marketplace.producers}")
    private String producers;
    @Value("${marketplace.products}")
    private String products;
    @Value("${marketplace.sellers}")
    private String sellers;
    @Value("${marketplace.descriptions}")
    private String descriptions;

    public void generateEntities(ProductOfferRepository repository) {
        List<ProductOffer> data = new ArrayList<>();
        fillData(data);
        repository.saveAll(data);
    }


    private void fillData(List<ProductOffer> data) {

/*
    List<Store> stores1 = sellers.stream().map(sellerName -> {
        Store store = new Store();
        store.setName(sellerName);
        store.setRate(random.nextFloat(5F));
        return store;
    }).collect(Collectors.toList());
*/
        List<Store> storeList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        String[] producerNames = producers.split(",");
        String[] sellerNames = sellers.split(",");
        String[] productNames = products.split(",");
        String[] descriptionArray = descriptions.split(",");


        Random random = new Random();
        for (String sellerName : sellerNames) {
            Store store = new Store();
            store.setName(sellerName);
            store.setRate(random.nextFloat(5F));
            storeList.add(store);
        }


        for (String productName : productNames) {
            Product product = new Product();
            product.setName(productName);
            product.setCategory("канцелярия и мебель");
            product.setManufacturer(producerNames[random.nextInt(producerNames.length - 1)]);
            product.setDescription(descriptionArray[random.nextInt(descriptionArray.length - 1)]);
            productList.add(product);

        }


        for (int i = 0; i < 50; i++) {
            int productIndex = random.nextInt(productList.size());
            int storeIndex = random.nextInt(storeList.size());
            ProductOffer productOffer = new ProductOffer();
            productOffer.setProduct(productList.get(productIndex));
            productOffer.setStore(storeList.get(storeIndex));
            productOffer.setAvailable(random.nextInt(100));
            data.add(productOffer);
        }
    }
}

