package ru.teamtwo.core.dtos.controller;

import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface GetByCustomerController<T> {
    ResponseEntity<Set<T>> getAllByCustomer(Long customerId);
    ResponseEntity<Set<Integer>> saveAllByCustomer(Long customerId, Set<T> objects);
}
