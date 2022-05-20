package ru.teamtwo.core.dtos.controller;

import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface GetByOrderController<T> {
    ResponseEntity<Set<T>> getAllByOrder(Integer orderId);
    ResponseEntity<Set<Integer>> saveAllByOrder(Integer orderId, Set<T> objects);
}
