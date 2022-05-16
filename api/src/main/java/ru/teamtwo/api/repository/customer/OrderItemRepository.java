package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
