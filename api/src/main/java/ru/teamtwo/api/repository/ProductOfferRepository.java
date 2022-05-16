package ru.teamtwo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.teamtwo.core.models.product.ProductOffer;

public interface ProductOfferRepository extends PagingAndSortingRepository<ProductOffer, Long> {
    @Query(value = "SELECT po FROM ProductOffer po where po.product.name LIKE %:productName%")
    Page<ProductOffer> getProductOffersByProductName(String productName, Pageable pageable);

    ProductOffer getProductOfferById(Integer id);
}