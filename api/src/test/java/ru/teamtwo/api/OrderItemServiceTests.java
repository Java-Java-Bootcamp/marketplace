package ru.teamtwo.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.api.repository.customer.OrderItemRepository;
import ru.teamtwo.api.service.customer.OrderItemService;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.models.customer.OrderItem;

import java.util.List;

@SpringBootTest
@Slf4j
class OrderItemServiceTests {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Test
    @Transactional
    void testAddItem(){
        int CUSTOMER_ID = 2;
        int ORDER_ID = 2;
        int ORDER_ITEM_ID = 2;
        int PRODUCT_OFFER_ID = 2;

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(CUSTOMER_ID);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(CUSTOMER_ID);
        orderDto.setId(ORDER_ID);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(ORDER_ITEM_ID);
        orderItemDto.setOrderId(ORDER_ID);
        orderItemDto.setProductOfferId(PRODUCT_OFFER_ID);
        orderItemDto.setQuantity(1);


        List<OrderItem> items = orderItemRepository.findAll();
        items.forEach(item -> {
            Assertions.assertNotEquals(ORDER_ITEM_ID, item.getId());
        });

        Integer orderItemId = orderItemService.addItem(orderItemDto);
        OrderItemDto orderItem = orderItemService.getItem(orderItemId);

        Assertions.assertEquals(orderItemDto.getOrderId(), orderItem.getOrderId());
        Assertions.assertEquals(orderItemDto.getProductOfferId(), orderItem.getProductOfferId());
    }
}