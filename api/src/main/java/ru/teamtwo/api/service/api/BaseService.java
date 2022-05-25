package ru.teamtwo.api.service.api;

import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.UnableToAddItemException;

import java.util.Set;

public interface BaseService<T> {
    T get(Long id) throws ItemNotFoundException;
    Set<Long> save(Set<T> dto) throws UnableToAddItemException;
}
