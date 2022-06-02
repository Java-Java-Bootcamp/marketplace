package ru.teamtwo.backend.service.api;

import java.util.Set;

public interface GetByOrderService<T> {
    Set<T> getAllByOrder(Long orderId);
}
