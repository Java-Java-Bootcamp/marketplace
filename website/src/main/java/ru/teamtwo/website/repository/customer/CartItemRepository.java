package ru.teamtwo.website.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.core.models.customer.CartItem;

import java.util.Set;

public interface CartItemRepository  extends JpaRepository<CartItem, Integer> {
    Set<CartItem> getCartItemsByCustomer_Id(Integer customer_id);
}
