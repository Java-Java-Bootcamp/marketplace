package ru.teamtwo.core.dtos.customer;

import java.time.Instant;

public record OrderDto (
        Long id,
        Long customerId,
        Instant createdOn
) {}
