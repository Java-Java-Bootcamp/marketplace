package ru.teamtwo.api.mappers.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntities;
import ru.teamtwo.api.models.product.Product;
import ru.teamtwo.core.dtos.product.ProductDto;

import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class ProductMapperTest {
    @Autowired
    BaseTestEntities baseTestEntities;
    @Autowired
    ProductMapper productMapper;
    Product product;
    ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = baseTestEntities.getProduct();
        productDto = new ProductDto(product.getId(),
                product.getName(),
                product.getCategory(),
                product.getModel(),
                product.getManufacturer(),
                product.getDescription(),
                product.getPrice(),
                product.getRating()
        );
    }

    @Test
    void convertToDto() {
        ProductDto convert = productMapper.convert(product);
        assertDtoAndEntityEqual(convert, product);
    }

    @Test
    void convertFromDto() {
        Product convert = productMapper.convert(productDto);
        assertDtoAndEntityEqual(productDto, convert);
    }
}