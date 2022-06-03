package ru.teamtwo.backend.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.teamtwo.backend.models.product.ProductOffer;

public interface ProductOfferRepository extends PagingAndSortingRepository<ProductOffer, Long>, JpaRepository<ProductOffer, Long> {
    Page<ProductOffer> getProductOffersByProduct_NameContains(String name, Pageable pageable);
}