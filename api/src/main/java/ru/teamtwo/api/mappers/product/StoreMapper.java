package ru.teamtwo.api.mappers.product;

import org.mapstruct.Mapper;
import ru.teamtwo.api.mappers.BaseMapper;
import ru.teamtwo.api.models.product.Store;
import ru.teamtwo.core.dtos.product.StoreDto;

@Mapper(componentModel = "spring")
public interface StoreMapper extends BaseMapper<Store, StoreDto> {
}
