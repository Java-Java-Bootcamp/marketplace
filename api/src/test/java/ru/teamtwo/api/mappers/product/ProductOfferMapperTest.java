package ru.teamtwo.api.mappers.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntities;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class ProductOfferMapperTest {
    @Autowired
    BaseTestEntities baseTestEntities;
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
        ProductOfferDto convert = productOfferMapper.convert(productOffer);
        assertDtoAndEntityEqual(convert, productOffer);
    }

    @Test
    void convertFromDto() {
        ProductOffer convert = productOfferMapper.convert(productOfferDto);
        assertDtoAndEntityEqual(productOfferDto, convert);
    }
}