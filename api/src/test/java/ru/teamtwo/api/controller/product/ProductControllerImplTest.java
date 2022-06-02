package ru.teamtwo.api.controller.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.controller.ControllerTestUtils;
import ru.teamtwo.api.controller.ControllerTestUtilsParams;
import ru.teamtwo.api.mappers.product.ProductMapper;
import ru.teamtwo.api.models.product.Product;
import ru.teamtwo.core.dtos.product.StoreDto;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerImplTest {
    String CONTROLLER_REQUEST_MAPPING = "product";
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Autowired
    MockMvc mockMvc;
    ControllerTestUtilsParams controllerTestUtilsParams;

    @BeforeEach
    void setUp() {
        controllerTestUtilsParams = new ControllerTestUtilsParams(
                mockMvc,
                CONTROLLER_REQUEST_MAPPING,
                baseTestEntities.getProduct(),
                baseTestEntities.getProductDto());
    }

    @Test
    void get() throws Exception {
        ControllerTestUtils.baseGetTest(controllerTestUtilsParams);
    }

    @Test
    void save() throws Exception {
        ControllerTestUtils.baseSaveTest(controllerTestUtilsParams);
    }
}