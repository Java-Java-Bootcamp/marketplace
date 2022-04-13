package ru.teamtwo.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.website.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
