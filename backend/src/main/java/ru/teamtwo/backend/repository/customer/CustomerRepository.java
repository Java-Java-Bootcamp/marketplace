package ru.teamtwo.backend.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.backend.models.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
