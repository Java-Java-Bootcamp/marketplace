package ru.teamtwo.backend.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.backend.models.customer.CartItem;
import ru.teamtwo.backend.repository.GetByCustomerRepository;

public interface CartItemRepository  extends JpaRepository<CartItem, Long>, GetByCustomerRepository<CartItem> {
}
