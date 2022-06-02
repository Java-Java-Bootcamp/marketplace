package ru.teamtwo.api.service.impl.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.mappers.customer.OrderItemMapper;
import ru.teamtwo.api.repository.customer.OrderItemRepository;
import ru.teamtwo.api.service.impl.ServiceTestUtils;
import ru.teamtwo.api.service.impl.ServiceTestUtilsParams;

@DataJpaTest
class OrderItemServiceImplTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderItemMapper orderItemMapper;
    @InjectMocks
    private OrderItemServiceImpl orderItemService;
    private ServiceTestUtilsParams serviceTestUtilsParams;

    @BeforeEach
    void setUp() {
        serviceTestUtilsParams = new ServiceTestUtilsParams(
                orderItemService,
                orderItemRepository,
                baseTestEntities.getOrderItem(),
                baseTestEntities.getOrderItemDto(),
                orderItemMapper);
    }

    @Test
    void get() {
        ServiceTestUtils.testBasicGet(serviceTestUtilsParams);
    }

    @Test
    void save() {
        ServiceTestUtils.testBasicSave(serviceTestUtilsParams);
    }

    @Test
    void getAllByOrder() {
        ServiceTestUtils.testGetByOrder(serviceTestUtilsParams);
    }
}