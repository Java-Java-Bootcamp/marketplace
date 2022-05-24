package ru.teamtwo.api.service.api;

import java.util.Set;

public interface BaseService<T> {
    T get(Long id);
    Set<Long> save(Set<T> dto);
}
