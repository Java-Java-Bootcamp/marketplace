package ru.teamtwo.backend.repository.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.models.product.Product;
import ru.teamtwo.backend.models.product.ProductOffer;
import ru.teamtwo.backend.models.product.Store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.backend.TestUtils.ANOTHER_NEW_NUMBER;
import static ru.teamtwo.backend.TestUtils.EMPTY_ID;
import static ru.teamtwo.backend.TestUtils.NEW_NUMBER;
import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_NUMBER;

@DataJpaTest
class ProductOfferRepositoryTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Autowired
    ProductOfferRepository productOfferRepository;
    Product product;
    Store store;
    ProductOffer productOffer;
    ProductOffer setupProductOffer;
    @BeforeEach
    void setUp() {
        product = baseTestEntities.getProduct();
        store = baseTestEntities.getStore();
        setupProductOffer = baseTestEntities.getProductOffer();
    }

    @Test
    void get(){
        assertThat(productOfferRepository.existsById(EMPTY_ID)).isFalse();
        assertThat(productOfferRepository.existsById(setupProductOffer.getId())).isTrue();
        assertThat(productOfferRepository.getById(setupProductOffer.getId())).isEqualTo(setupProductOffer);
    }

    @Test
    void save() {
        //null fields
        productOffer = new ProductOffer(null, null, null, null);
        assertThatThrownBy(()-> productOfferRepository.save(productOffer)).isInstanceOf(DataAccessException.class);

        //basic save
        productOffer = new ProductOffer(null, product, store, UNIMPORTANT_NUMBER);
        Long id = productOfferRepository.save(productOffer).getId();

        //overwrite
        productOffer = new ProductOffer(id, product, store, NEW_NUMBER);
        ProductOffer newProductOffer = productOfferRepository.save(productOffer);
        assertThat(newProductOffer.getId()).isEqualTo(id);
        assertThat(newProductOffer.getProduct().getId()).isEqualTo(UNIMPORTANT_ID);
        assertThat(newProductOffer.getStore().getId()).isEqualTo(UNIMPORTANT_ID);
        assertThat(newProductOffer.getQuantity()).isEqualTo(NEW_NUMBER);

        //edit and save
        newProductOffer.setQuantity(ANOTHER_NEW_NUMBER);
        newProductOffer = productOfferRepository.save(newProductOffer);
        assertThat(newProductOffer.getQuantity()).isEqualTo(ANOTHER_NEW_NUMBER);
    }

    @Test
    void query() {
        //productOfferRepository.getProductOffersByProductName()
    }
}