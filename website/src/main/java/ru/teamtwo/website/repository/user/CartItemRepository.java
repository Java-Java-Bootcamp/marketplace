package ru.teamtwo.website.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.website.model.user.CartItem;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {
}
