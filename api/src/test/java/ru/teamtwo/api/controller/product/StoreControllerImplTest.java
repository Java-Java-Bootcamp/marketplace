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
import ru.teamtwo.api.mappers.product.StoreMapper;
import ru.teamtwo.api.models.product.Store;
import ru.teamtwo.core.dtos.product.StoreDto;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerImplTest {
    String CONTROLLER_REQUEST_MAPPING = "store";
    @Autowired
    BaseTestEntities baseTestEntities;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    StoreMapper storeMapper;
    Store store;

    @BeforeEach
    void setUp() {
        store = baseTestEntities.getStore();
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(ControllerTestUtils.get(CONTROLLER_REQUEST_MAPPING, store)).andReturn();
        ControllerTestUtils.assertContentAndEntityEqual(result.getResponse(), StoreDto.class, store);
    }

    @Test
    void save() throws Exception {
        MvcResult result = mockMvc.perform(ControllerTestUtils.post(CONTROLLER_REQUEST_MAPPING, storeMapper.convert(store))).andReturn();
        ControllerTestUtils.assertContentAndEntityIdsEqual(result.getResponse(), store);
    }

    @Test
    void query() {
    }
}