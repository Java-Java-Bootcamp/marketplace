package ru.teamtwo.api.repository.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import ru.teamtwo.api.BaseTestEntities;
import ru.teamtwo.api.models.product.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.api.TestUtils.ANOTHER_NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.EMPTY_ID;
import static ru.teamtwo.api.TestUtils.NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_STRING;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    BaseTestEntities baseTestEntities;
    @Autowired
    ProductRepository productRepository;
    Product product;
    Product setupProduct;
    @BeforeEach
    void setUp() {
        setupProduct = baseTestEntities.getProduct();
    }

    @Test
    void get(){
        assertThat(productRepository.existsById(EMPTY_ID)).isFalse();
        assertThat(productRepository.existsById(setupProduct.getId())).isTrue();
        assertThat(productRepository.getById(setupProduct.getId())).isEqualTo(setupProduct);
    }

    @Test
    void save() {
        //null fields
        product = new Product();
        assertThatThrownBy(()-> productRepository.save(product)).isInstanceOf(DataAccessException.class);

        //basic save
        product = new Product(null,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_NUMBER,
                UNIMPORTANT_NUMBER);
        Long id = productRepository.save(product).getId();

        //overwrite
        product = new Product(id,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_NUMBER,
                NEW_NUMBER);
        Product newProduct = productRepository.save(product);
        assertThat(newProduct.getId()).isEqualTo(id);
        assertThat(newProduct.getCategory()).isEqualTo(UNIMPORTANT_STRING);
        assertThat(newProduct.getRating()).isEqualTo(NEW_NUMBER);

        //edit and save
        newProduct.setRating(ANOTHER_NEW_NUMBER);
        newProduct = productRepository.save(newProduct);
        assertThat(newProduct.getRating()).isEqualTo(ANOTHER_NEW_NUMBER);
    }
}