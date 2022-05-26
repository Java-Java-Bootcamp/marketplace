package ru.teamtwo.api.controller.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.teamtwo.api.BaseTestEntities;
import ru.teamtwo.api.controller.ControllerTestUtils;
import ru.teamtwo.api.mappers.product.ProductOfferMapper;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

@SpringBootTest
@AutoConfigureMockMvc
class ProductOfferControllerImplTest {
    String CONTROLLER_REQUEST_MAPPING = "product_offer";
    @Autowired
    BaseTestEntities baseTestEntities;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductOfferMapper productOfferMapper;
    ProductOffer productOffer;

    @BeforeEach
    void setUp() {
        productOffer = baseTestEntities.getProductOffer();
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(ControllerTestUtils.get(CONTROLLER_REQUEST_MAPPING, productOffer)).andReturn();
        ControllerTestUtils.assertContentAndEntityEqual(result.getResponse(), ProductOfferDto.class, productOffer);
    }

    @Test
    void save() throws Exception {
        MvcResult result = mockMvc.perform(ControllerTestUtils.post(CONTROLLER_REQUEST_MAPPING,productOfferMapper.convert(productOffer))).andReturn();
        ControllerTestUtils.assertContentAndEntityIdsEqual(result.getResponse(), productOffer);
    }

    @Test
    void query() {
    }
}