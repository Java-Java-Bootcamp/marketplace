package ru.teamtwo.api.service.api;

import java.util.Set;

public interface GetByOrderService<T> {
    Set<T> getAllByOrder(Long orderId);
}
