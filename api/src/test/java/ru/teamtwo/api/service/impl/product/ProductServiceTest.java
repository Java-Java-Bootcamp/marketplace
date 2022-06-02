package ru.teamtwo.api.service.impl.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.mappers.product.ProductMapper;
import ru.teamtwo.api.repository.product.ProductRepository;
import ru.teamtwo.api.service.impl.ServiceTestUtils;
import ru.teamtwo.api.service.impl.ServiceTestUtilsParams;

@DataJpaTest
class ProductServiceTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;
    private ServiceTestUtilsParams serviceTestUtilsParams;

    @BeforeEach
    void setUp() {
        serviceTestUtilsParams = new ServiceTestUtilsParams(
                productService,
                productRepository,
                baseTestEntities.getProduct(),
                baseTestEntities.getProductDto(),
                productMapper);
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