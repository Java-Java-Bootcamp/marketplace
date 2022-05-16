package ru.teamtwo.core.mappers.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.core.models.product.ProductOffer;

@Mapper(componentModel = "spring")
public interface ProductOfferMapper {
    @Mappings({
            @Mapping(target="productId", source="productOffer.product.id"),
            @Mapping(target="storeId", source="productOffer.store.id")
    })
    ProductOfferDto convert(ProductOffer productOffer);
    ProductOffer convert(ProductOfferDto productOfferDto);
}
