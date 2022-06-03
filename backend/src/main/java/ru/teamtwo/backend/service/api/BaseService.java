package ru.teamtwo.backend.service.api;

import ru.teamtwo.backend.exception.ItemNotFoundException;
import ru.teamtwo.backend.exception.UnableToAddItemException;

import java.util.Set;

public interface BaseService<T> {
    T get(Long id) throws ItemNotFoundException;
    Set<Long> save(Set<T> dto) throws UnableToAddItemException;
}
