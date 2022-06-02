package ru.teamtwo.api.service.impl.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.mappers.product.StoreMapper;
import ru.teamtwo.api.repository.product.StoreRepository;
import ru.teamtwo.api.service.impl.ServiceTestUtils;
import ru.teamtwo.api.service.impl.ServiceTestUtilsParams;

@DataJpaTest
class StoreServiceTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreMapper storeMapper;
    @InjectMocks
    private StoreServiceImpl storeService;
    private ServiceTestUtilsParams serviceTestUtilsParams;

    @BeforeEach
    void setUp() {
        serviceTestUtilsParams = new ServiceTestUtilsParams(
                storeService,
                storeRepository,
                baseTestEntities.getStore(),
                baseTestEntities.getStoreDto(),
                storeMapper);
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