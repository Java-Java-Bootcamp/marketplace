package ru.teamtwo.backend.mappers;

import ru.teamtwo.backend.models.BaseEntity;

public interface BaseMapper<T0 extends BaseEntity, T1 extends Record> {
    T0 convertToEntity(T1 object);
    T1 convertToDto(T0 object);
}
