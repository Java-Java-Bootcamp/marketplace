package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.CartItem;

import java.util.Set;

public interface CartItemRepository  extends JpaRepository<CartItem, Integer> {
    Set<CartItem> getCartItemsByCustomer_Id(Integer customer_id);
}
