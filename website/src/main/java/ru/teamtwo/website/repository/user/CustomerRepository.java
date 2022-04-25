package ru.teamtwo.website.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.core.models.user.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
