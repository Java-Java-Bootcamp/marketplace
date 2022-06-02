package ru.teamtwo.backend.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.models.customer.Customer;
import ru.teamtwo.backend.models.customer.Order;
import ru.teamtwo.backend.models.customer.OrderItem;
import ru.teamtwo.backend.models.product.ProductOffer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.backend.TestUtils.ANOTHER_NEW_NUMBER;
import static ru.teamtwo.backend.TestUtils.EMPTY_ID;
import static ru.teamtwo.backend.TestUtils.NEW_NUMBER;
import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_NUMBER;

@DataJpaTest
class OrderItemRepositoryTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Autowired
    OrderItemRepository orderItemRepository;
    Order order;
    OrderItem orderItem;
    OrderItem setupOrderItem;
    Customer customer;
    ProductOffer productOffer;

    @BeforeEach
    void setUp() {
        customer = baseTestEntities.getCustomer();
        productOffer = baseTestEntities.getProductOffer();
        order = baseTestEntities.getOrder();
        setupOrderItem = baseTestEntities.getOrderItem();
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
        orderItem = new OrderItem(null, order, productOffer, UNIMPORTANT_NUMBER);
        Long id = orderItemRepository.save(orderItem).getId();

        //overwrite
        orderItem = new OrderItem(id, order, productOffer, NEW_NUMBER);
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
        assertThat(orderItemRepository.getByOrder_Id(null)).isEmpty();
        assertThat(orderItemRepository.getByOrder_Id(EMPTY_ID)).isEmpty();
        assertThat(orderItemRepository.getByOrder_Id(setupOrderItem.getOrder().getId())).contains(setupOrderItem);
    }
}