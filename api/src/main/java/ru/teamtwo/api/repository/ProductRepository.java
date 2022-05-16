package ru.teamtwo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.core.models.product.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
