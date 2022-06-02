package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.repository.GetByCustomerRepository;

public interface CartItemRepository  extends JpaRepository<CartItem, Long>, GetByCustomerRepository<CartItem> {
}
