package ru.teamtwo.core.dtos.controller;

import org.springframework.http.ResponseEntity;

public interface BaseController<T> {
    ResponseEntity<T> get(Long id);
    ResponseEntity<Integer> save(T dto);
}
