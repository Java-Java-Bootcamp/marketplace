package ru.teamtwo.backend.controller.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.controller.ControllerTestUtils;
import ru.teamtwo.backend.controller.ControllerTestUtilsParams;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerImplTest {
    String CONTROLLER_REQUEST_MAPPING = "order";
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
                baseTestEntities.getOrder(),
                baseTestEntities.getOrderDto());
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