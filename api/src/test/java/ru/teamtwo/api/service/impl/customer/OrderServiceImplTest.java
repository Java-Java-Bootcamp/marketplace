package ru.teamtwo.api.service.impl.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.mappers.customer.OrderMapper;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.api.service.impl.ServiceTestUtils;
import ru.teamtwo.api.service.impl.ServiceTestUtilsParams;

@DataJpaTest
class OrderServiceImplTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;
    private ServiceTestUtilsParams serviceTestUtilsParams;

    @BeforeEach
    void setUp() {
        serviceTestUtilsParams = new ServiceTestUtilsParams(
                orderService,
                orderRepository,
                baseTestEntities.getOrder(),
                baseTestEntities.getOrderDto(),
                orderMapper);
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
    void getAllByCustomer() {
        ServiceTestUtils.testGetByCustomer(serviceTestUtilsParams);
    }
}