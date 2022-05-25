package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.OrderItem;

import java.util.Set;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Set<OrderItem> getOrderItemsByOrder_Id(Long orderId);
}
