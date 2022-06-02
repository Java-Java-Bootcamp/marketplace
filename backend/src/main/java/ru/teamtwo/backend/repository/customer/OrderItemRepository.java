package ru.teamtwo.backend.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.backend.models.customer.OrderItem;
import ru.teamtwo.backend.repository.GetByOrderRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, GetByOrderRepository<OrderItem> {
}
