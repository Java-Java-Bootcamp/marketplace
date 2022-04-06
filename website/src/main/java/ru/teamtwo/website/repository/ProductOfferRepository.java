package ru.teamtwo.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.teamtwo.website.model.ProductOffer;

import java.util.List;
import java.util.Optional;

public interface ProductOfferRepository extends JpaRepository<ProductOffer, Long> {
    @Query ("SELECT po FROM ProductOffer po where po.product.name LIKE %:productName%")
    Iterable<ProductOffer> getProductOffersByProductName(String productName);

}