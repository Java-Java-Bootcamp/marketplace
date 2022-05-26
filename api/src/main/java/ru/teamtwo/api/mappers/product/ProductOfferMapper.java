package ru.teamtwo.api.mappers.product;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.repository.product.ProductRepository;
import ru.teamtwo.api.repository.product.StoreRepository;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

@Mapper(componentModel = "spring")
public abstract class ProductOfferMapper {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Mappings({
            @Mapping(target="productId", source="productOffer.product.id"),
            @Mapping(target="storeId", source="productOffer.store.id")
    })
    public abstract ProductOfferDto convert(ProductOffer productOffer);
    public abstract ProductOffer convert(ProductOfferDto productOfferDto);
    @AfterMapping
    protected void afterMapping(ProductOfferDto productOfferDto, @MappingTarget ProductOffer.ProductOfferBuilder productOfferBuilder) {
        productOfferBuilder.product(productRepository.getById(productOfferDto.productId()));
        productOfferBuilder.store(storeRepository.getById(productOfferDto.storeId()));
    }
}
