package ru.teamtwo.api.mappers.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.repository.customer.OrderItemRepository;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;
import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class OrderItemMapperTest {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductOfferRepository productOfferRepository;
    OrderItem orderItem;
    OrderItemDto orderItemDto;
    @BeforeEach
    void setUp() {
        orderItem = orderItemRepository.save(new OrderItem(null,
                orderRepository.getById(UNIMPORTANT_ID),
                productOfferRepository.getById(UNIMPORTANT_ID),
                UNIMPORTANT_NUMBER));
        orderItemDto = new OrderItemDto(orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProductOffer().getId(),
                orderItem.getQuantity());
    }

    @Test
    void convertToDto() {
        OrderItemDto convert = orderItemMapper.convert(orderItem);
        assertDtoAndEntityEqual(convert, orderItem);
    }

    @Test
    void convertFromDto() {
        OrderItem convert = orderItemMapper.convert(orderItemDto);
        assertDtoAndEntityEqual(orderItemDto, convert);
    }
}