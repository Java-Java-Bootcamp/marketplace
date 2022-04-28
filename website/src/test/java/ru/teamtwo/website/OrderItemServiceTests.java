package ru.teamtwo.website;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.models.customer.Customer;
import ru.teamtwo.core.models.customer.Order;
import ru.teamtwo.core.models.customer.OrderItem;
import ru.teamtwo.website.repository.customer.CustomerRepository;
import ru.teamtwo.website.repository.customer.OrderItemRepository;
import ru.teamtwo.website.service.customer.CustomerService;
import ru.teamtwo.website.service.customer.OrderItemService;

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
