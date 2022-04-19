package ru.teamtwo.website.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.website.model.user.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
