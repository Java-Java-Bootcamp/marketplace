package ru.teamtwo.backend.service.impl.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.mappers.customer.CartItemMapper;
import ru.teamtwo.backend.repository.customer.CartItemRepository;
import ru.teamtwo.backend.service.impl.ServiceTestUtils;
import ru.teamtwo.backend.service.impl.ServiceTestUtilsParams;

@DataJpaTest
class CartItemServiceImplTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartItemMapper cartItemMapper;
    @InjectMocks
    private CartItemServiceImpl cartItemService;
    private ServiceTestUtilsParams serviceTestUtilsParams;

    @BeforeEach
    void setUp() {
        serviceTestUtilsParams = new ServiceTestUtilsParams(
                cartItemService,
                cartItemRepository,
                baseTestEntities.getCartItem(),
                baseTestEntities.getCartItemDto(),
                cartItemMapper);
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