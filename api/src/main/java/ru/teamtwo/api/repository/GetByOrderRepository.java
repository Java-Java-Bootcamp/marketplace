package ru.teamtwo.api.repository;

import java.util.Set;

public interface GetByOrderRepository<T> {
    Set<T> getByOrder_Id(Long orderId);
}
