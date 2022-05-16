package ru.teamtwo.api.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.product.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
