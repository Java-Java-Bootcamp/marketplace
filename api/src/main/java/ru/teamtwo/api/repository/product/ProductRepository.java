package ru.teamtwo.api.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
