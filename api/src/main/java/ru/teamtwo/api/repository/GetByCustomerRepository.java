package ru.teamtwo.api.repository;

import java.util.Set;

public interface GetByCustomerRepository<T> {
    Set<T> getByCustomer_Id(Long customerId);
}
