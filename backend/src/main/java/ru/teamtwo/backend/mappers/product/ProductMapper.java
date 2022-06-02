package ru.teamtwo.backend.mappers.product;

import org.mapstruct.Mapper;
import ru.teamtwo.backend.mappers.BaseMapper;
import ru.teamtwo.backend.models.product.Product;
import ru.teamtwo.core.dtos.product.ProductDto;

@Mapper(componentModel = "spring")
public interface  ProductMapper extends BaseMapper<Product, ProductDto> {
}
