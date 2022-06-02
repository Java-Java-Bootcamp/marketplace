package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.repository.GetByCustomerRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, GetByCustomerRepository<Order> {
}
