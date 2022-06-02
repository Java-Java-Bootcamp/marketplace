package ru.teamtwo.api.mappers.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.core.dtos.customer.OrderDto;

import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class OrderMapperTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Autowired
    OrderMapper orderMapper;
    Order order;
    OrderDto orderDto;
    @BeforeEach
    void setUp() {
        order = baseTestEntities.getOrder();
        orderDto = new OrderDto(order.getId(), order.getCustomer().getId(), order.getCreatedOn());
    }

    @Test
    void convertToDto() {
        OrderDto convert = orderMapper.convertToDto(order);
        assertDtoAndEntityEqual(convert, order);
    }

    @Test
    void convertFromDto() {
        Order convert = orderMapper.convertToEntity(orderDto);
        assertDtoAndEntityEqual(orderDto, convert);
    }
}