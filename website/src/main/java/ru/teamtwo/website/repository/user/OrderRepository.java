package ru.teamtwo.website.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.core.models.user.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
