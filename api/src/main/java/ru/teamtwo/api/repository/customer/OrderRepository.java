package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
