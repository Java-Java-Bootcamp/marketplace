package ru.teamtwo.core.mappers;

import org.mapstruct.Mapper;
import ru.teamtwo.core.dtos.ProductDto;
import ru.teamtwo.core.models.Product;

@Mapper(componentModel = "spring")
public interface  ProductMapper {
    ProductDto convert(Product product);
    Product convert(ProductDto productDTO);
}
