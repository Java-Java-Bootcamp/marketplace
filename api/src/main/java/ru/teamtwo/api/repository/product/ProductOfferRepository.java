package ru.teamtwo.api.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.teamtwo.api.models.product.ProductOffer;

public interface ProductOfferRepository extends PagingAndSortingRepository<ProductOffer, Long>, JpaRepository<ProductOffer, Long> {
    @Query(value = "SELECT po FROM ProductOffer po where po.product.name LIKE %:productName%")
    Page<ProductOffer> getProductOffersByProductName(String productName, Pageable pageable);

    ProductOffer getProductOfferById(Long id);
}