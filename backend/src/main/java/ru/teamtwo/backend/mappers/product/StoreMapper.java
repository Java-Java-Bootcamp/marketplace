package ru.teamtwo.backend.mappers.product;

import org.mapstruct.Mapper;
import ru.teamtwo.backend.mappers.BaseMapper;
import ru.teamtwo.backend.models.product.Store;
import ru.teamtwo.core.dtos.product.StoreDto;

@Mapper(componentModel = "spring")
public interface StoreMapper extends BaseMapper<Store, StoreDto> {
}
