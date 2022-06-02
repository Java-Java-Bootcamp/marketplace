package ru.teamtwo.backend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.backend.models.product.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
