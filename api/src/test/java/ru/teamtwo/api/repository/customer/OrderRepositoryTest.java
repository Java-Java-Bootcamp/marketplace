package ru.teamtwo.api.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import ru.teamtwo.api.BaseTestEntities;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.repository.product.ProductOfferRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.api.TestUtils.EMPTY_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_INSTANT;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    BaseTestEntities baseTestEntities;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductOfferRepository productOfferRepository;
    @Autowired
    CustomerRepository customerRepository;
    Order setupOrder;
    Order order;
    Customer customer;
    ProductOffer productOffer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.getById(UNIMPORTANT_ID);
        productOffer = productOfferRepository.getById(UNIMPORTANT_ID);
        setupOrder = new Order(null, customer, UNIMPORTANT_INSTANT);
        setupOrder = orderRepository.save(setupOrder);
    }

    @Test
    void get() {
        assertThat(orderRepository.existsById(EMPTY_ID)).isFalse();
        assertThat(orderRepository.existsById(setupOrder.getId())).isTrue();
        assertThat(orderRepository.getById(setupOrder.getId())).isEqualTo(setupOrder);
    }

    @Test
    void save() {
        //null fields
        order = new Order(null, null, null);
        assertThatThrownBy(() -> orderRepository.save(order)).isInstanceOf(DataAccessException.class);

        //basic save
        order = new Order(null, customer, UNIMPORTANT_INSTANT);
        Long id = orderRepository.save(order).getId();

        //overwrite
        order = new Order(id, customer, UNIMPORTANT_INSTANT);
        Order newOrder = orderRepository.save(order);
        assertThat(newOrder.getId()).isEqualTo(id);
        assertThat(newOrder.getCustomer()).isEqualTo(customer);

        //TODO: edit and save check
    }

    @Test
    void getOrdersByCustomer_Id() {
        assertThat(orderRepository.getOrdersByCustomer_Id(null)).isEmpty();
        assertThat(orderRepository.getOrdersByCustomer_Id(EMPTY_ID)).isEmpty();
        assertThat(orderRepository.getOrdersByCustomer_Id(setupOrder.getCustomer().getId())).contains(setupOrder);
    }
}