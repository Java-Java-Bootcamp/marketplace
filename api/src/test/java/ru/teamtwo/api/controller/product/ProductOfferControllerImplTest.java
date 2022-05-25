package ru.teamtwo.api.controller.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductOfferControllerImplTest {

    @Autowired
    ProductOfferControllerImpl productOfferController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void get() {
        //productOfferController.get()
    }

    @Test
    void save() {
    }

    @Test
    void query() {
    }
}