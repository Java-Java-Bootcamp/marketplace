package ru.teamtwo.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.teamtwo.website.model.ProductOffer;

import java.util.List;
import java.util.Optional;

public interface ProductOfferRepository extends JpaRepository<ProductOffer, Long> {
    @Query ("select id, product, store, available from ProductOffer where product.name like (:productName)")
    List<ProductOffer> getProductOffersByProductName(String productName);

}