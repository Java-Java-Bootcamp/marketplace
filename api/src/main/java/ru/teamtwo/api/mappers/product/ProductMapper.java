package ru.teamtwo.api.mappers.product;

import org.mapstruct.Mapper;
import ru.teamtwo.api.mappers.BaseMapper;
import ru.teamtwo.api.models.product.Product;
import ru.teamtwo.core.dtos.product.ProductDto;

@Mapper(componentModel = "spring")
public interface  ProductMapper extends BaseMapper<Product, ProductDto> {
}
