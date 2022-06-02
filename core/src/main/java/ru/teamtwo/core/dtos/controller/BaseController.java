package ru.teamtwo.core.dtos.controller;

import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface BaseController<T> {
    ResponseEntity<T> get(Long id);
    ResponseEntity<Set<Long>> save(Set<T> dto);
}
