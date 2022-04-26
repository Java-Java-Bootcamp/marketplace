package ru.teamtwo.website.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.core.models.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
