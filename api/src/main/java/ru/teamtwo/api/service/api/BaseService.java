package ru.teamtwo.api.service.api;

public interface BaseService<T> {
    T get(Long id);
    Long save(T dto);
}
