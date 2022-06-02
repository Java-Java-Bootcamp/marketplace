package ru.teamtwo.backend.mappers.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.models.product.ProductOffer;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import static ru.teamtwo.backend.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class ProductOfferMapperTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Autowired
    ProductOfferMapper productOfferMapper;
    ProductOffer productOffer;
    ProductOfferDto productOfferDto;

    @BeforeEach
    void setUp() {
        productOffer = baseTestEntities.getProductOffer();
        productOfferDto = new ProductOfferDto(productOffer.getId(),
                productOffer.getProduct().getId(),
                productOffer.getStore().getId(),
                productOffer.getQuantity()
        );
    }

    @Test
    void convertToDto() {
        ProductOfferDto convert = productOfferMapper.convertToDto(productOffer);
        assertDtoAndEntityEqual(convert, productOffer);
    }

    @Test
    void convertFromDto() {
        ProductOffer convert = productOfferMapper.convertToEntity(productOfferDto);
        assertDtoAndEntityEqual(productOfferDto, convert);
    }
}