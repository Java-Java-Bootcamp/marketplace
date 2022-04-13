package ru.teamtwo.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.website.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
