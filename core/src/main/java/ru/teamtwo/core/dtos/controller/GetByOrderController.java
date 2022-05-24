package ru.teamtwo.core.dtos.controller;

import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface GetByOrderController<T> {
    ResponseEntity<Set<T>> getAllByOrder(Long orderId);
}
