package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.Order;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> getOrdersByCustomer_Id(Long customerId);
}
