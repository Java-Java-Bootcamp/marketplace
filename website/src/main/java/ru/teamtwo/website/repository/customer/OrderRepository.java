package ru.teamtwo.website.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.core.models.customer.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
