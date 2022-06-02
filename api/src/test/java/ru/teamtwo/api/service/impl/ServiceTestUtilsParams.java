package ru.teamtwo.api.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.mappers.BaseMapper;
import ru.teamtwo.api.models.BaseEntity;
import ru.teamtwo.api.service.api.BaseService;

public record ServiceTestUtilsParams(BaseService service,
                                     JpaRepository repository,
                                     BaseEntity entity,
                                     Record dto,
                                     BaseMapper mapper) {
}
