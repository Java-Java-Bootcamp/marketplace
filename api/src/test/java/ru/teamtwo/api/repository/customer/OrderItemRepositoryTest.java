package ru.teamtwo.api.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.repository.product.ProductOfferRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.api.TestUtils.ANOTHER_NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.EMPTY_ID;
import static ru.teamtwo.api.TestUtils.NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_INSTANT;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class OrderItemRepositoryTest {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductOfferRepository productOfferRepository;
    @Autowired
    CustomerRepository customerRepository;
    Order setupOrder;
    OrderItem orderItem;
    OrderItem setupOrderItem;
    Customer customer;
    ProductOffer productOffer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.getById(UNIMPORTANT_ID);
        productOffer = productOfferRepository.getById(UNIMPORTANT_ID);
        setupOrder = new Order(null, customer, UNIMPORTANT_INSTANT);
        setupOrder = orderRepository.save(setupOrder);
        setupOrderItem = new OrderItem(null, setupOrder, productOffer, UNIMPORTANT_NUMBER);
        setupOrderItem = orderItemRepository.save(setupOrderItem);
    }

    @Test
    void get() {
        assertThat(orderItemRepository.existsById(EMPTY_ID)).isFalse();
        assertThat(orderItemRepository.existsById(setupOrderItem.getId())).isTrue();
        assertThat(orderItemRepository.getById(setupOrderItem.getId())).isEqualTo(setupOrderItem);
    }

    @Test
    void save() {
        //null fields
        orderItem = new OrderItem(null, null, null, null);
        assertThatThrownBy(() -> orderItemRepository.save(orderItem)).isInstanceOf(DataAccessException.class);

        //basic save
        orderItem = new OrderItem(null, setupOrder, productOffer, UNIMPORTANT_NUMBER);
        Long id = orderItemRepository.save(orderItem).getId();

        //overwrite
        orderItem = new OrderItem(id, setupOrder, productOffer, NEW_NUMBER);
        OrderItem newOrderItem = orderItemRepository.save(orderItem);
        assertThat(newOrderItem.getId()).isEqualTo(id);
        assertThat(newOrderItem.getOrder().getId()).isEqualTo(UNIMPORTANT_ID);
        assertThat(newOrderItem.getQuantity()).isEqualTo(NEW_NUMBER);

        //edit and save
        newOrderItem.setQuantity(ANOTHER_NEW_NUMBER);
        newOrderItem = orderItemRepository.save(newOrderItem);
        assertThat(newOrderItem.getQuantity()).isEqualTo(ANOTHER_NEW_NUMBER);
    }

    @Test
    void getOrderItemsByOrder_Id() {
        assertThat(orderItemRepository.getOrderItemsByOrder_Id(null)).isEmpty();
        assertThat(orderItemRepository.getOrderItemsByOrder_Id(EMPTY_ID)).isEmpty();
        assertThat(orderItemRepository.getOrderItemsByOrder_Id(setupOrderItem.getOrder().getId())).contains(setupOrderItem);
    }
}