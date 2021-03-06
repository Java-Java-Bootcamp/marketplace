package ru.teamtwo.backend.service.impl.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.mappers.product.ProductOfferMapper;
import ru.teamtwo.backend.repository.product.ProductOfferRepository;
import ru.teamtwo.backend.service.impl.ServiceTestUtils;
import ru.teamtwo.backend.service.impl.ServiceTestUtilsParams;

@DataJpaTest
class ProductOfferServiceTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Mock
    private ProductOfferRepository productOfferRepository;
    @Mock
    private ProductOfferMapper productOfferMapper;
    @InjectMocks
    private ProductOfferServiceImpl productOfferService;
    private ServiceTestUtilsParams serviceTestUtilsParams;

    @BeforeEach
    void setUp() {
        serviceTestUtilsParams = new ServiceTestUtilsParams(
                productOfferService,
                productOfferRepository,
                baseTestEntities.getProductOffer(),
                baseTestEntities.getProductOfferDto(),
                productOfferMapper);
    }

    @Test
    void get() {
        ServiceTestUtils.testBasicGet(serviceTestUtilsParams);
    }

    @Test
    void save() {
        ServiceTestUtils.testBasicSave(serviceTestUtilsParams);
    }
}