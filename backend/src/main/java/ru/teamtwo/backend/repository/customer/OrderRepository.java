package ru.teamtwo.backend.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.backend.models.customer.Order;
import ru.teamtwo.backend.repository.GetByCustomerRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, GetByCustomerRepository<Order> {
}
