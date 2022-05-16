package ru.teamtwo.core.mappers.product;

import org.mapstruct.Mapper;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.core.models.product.Product;

@Mapper(componentModel = "spring")
public interface  ProductMapper {
    ProductDto convert(Product product);
    Product convert(ProductDto productDTO);
}
