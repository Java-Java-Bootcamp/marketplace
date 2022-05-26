package ru.teamtwo.api.mappers.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.core.dtos.customer.OrderDto;

import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_INSTANT;
import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    Order order;
    OrderDto orderDto;
    @BeforeEach
    void setUp() {
        order = orderRepository.save(new Order(null,
                customerRepository.getById(UNIMPORTANT_ID),
                UNIMPORTANT_INSTANT));
        orderDto = new OrderDto(order.getId(),
                order.getCustomer().getId(),
                order.getCreatedOn());
    }

    @Test
    void convertToDto() {
        OrderDto convert = orderMapper.convert(order);
        assertDtoAndEntityEqual(convert, order);
    }

    @Test
    void convertFromDto() {
        Order convert = orderMapper.convert(orderDto);
        assertDtoAndEntityEqual(orderDto, convert);
    }
}