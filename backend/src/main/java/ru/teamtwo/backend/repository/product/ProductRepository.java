package ru.teamtwo.backend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.backend.models.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
