package ru.teamtwo.core.dtos.customer;

import java.time.Instant;

public record OrderDto (
    Integer id,
    Integer customerId,
    Instant createdOn
) {}
