package ru.teamtwo.backend.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.backend.mappers.BaseMapper;
import ru.teamtwo.backend.models.BaseEntity;
import ru.teamtwo.backend.service.api.BaseService;

public record ServiceTestUtilsParams(BaseService service,
                                     JpaRepository repository,
                                     BaseEntity entity,
                                     Record dto,
                                     BaseMapper mapper) {
}
