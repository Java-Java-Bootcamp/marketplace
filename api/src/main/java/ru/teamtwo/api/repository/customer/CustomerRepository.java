package ru.teamtwo.api.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.models.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
