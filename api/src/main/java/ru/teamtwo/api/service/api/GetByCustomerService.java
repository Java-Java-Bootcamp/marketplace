package ru.teamtwo.api.service.api;

import java.util.Set;

public interface GetByCustomerService<T> {
    Set<T> getAllByCustomer(Long customerId);
}
