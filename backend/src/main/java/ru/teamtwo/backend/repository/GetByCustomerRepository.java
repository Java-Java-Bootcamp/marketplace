package ru.teamtwo.backend.repository;

import java.util.Set;

public interface GetByCustomerRepository<T> {
    Set<T> getByCustomer_Id(Long customerId);
}
