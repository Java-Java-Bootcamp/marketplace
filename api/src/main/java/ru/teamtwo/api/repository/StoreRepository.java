package ru.teamtwo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.core.models.product.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
