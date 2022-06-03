package ru.teamtwo.backend.service.impl.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.mappers.product.StoreMapper;
import ru.teamtwo.backend.repository.product.StoreRepository;
import ru.teamtwo.backend.service.impl.ServiceTestUtils;
import ru.teamtwo.backend.service.impl.ServiceTestUtilsParams;

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