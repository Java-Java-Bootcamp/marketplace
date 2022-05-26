package ru.teamtwo.api.controller.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Slf4j
@WebMvcTest
class ProductOfferControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductOfferControllerImpl productOfferController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("1")).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void save() {
    }

    @Test
    void query() {
    }
}