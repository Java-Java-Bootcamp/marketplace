package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.repository.GetByOrderRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, GetByOrderRepository<OrderItem> {
}
