package ru.teamtwo.website;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.models.customer.Order;
import ru.teamtwo.website.repository.customer.OrderRepository;
import ru.teamtwo.website.service.customer.OrderService;

import java.util.List;

@SpringBootTest
@Slf4j
class OrderServiceTests {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    void testAddOrder(){

        int CUSTOMER_ID = 2;
        int ORDER_ID = 2;

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(CUSTOMER_ID);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(CUSTOMER_ID);
        orderDto.setId(ORDER_ID);

        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> {
            Assertions.assertNotEquals(ORDER_ID, order.getId());
        });

        Integer orderId = orderService.addOrder(orderDto);
        OrderDto newOrder = orderService.getOrderById(orderId);

        Assertions.assertEquals(newOrder.getId(), ORDER_ID);
        Assertions.assertEquals(newOrder.getCustomerId(), CUSTOMER_ID);
    }
}
