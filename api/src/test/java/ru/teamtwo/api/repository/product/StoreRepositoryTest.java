package ru.teamtwo.api.repository.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.models.product.Store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.api.TestUtils.ANOTHER_NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.EMPTY_ID;
import static ru.teamtwo.api.TestUtils.NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_STRING;

@DataJpaTest
class StoreRepositoryTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Autowired
    StoreRepository storeRepository;
    Store store;
    Store setupStore;
    @BeforeEach
    void setUp() {
        setupStore = baseTestEntities.getStore();
    }

    @Test
    void get(){
        assertThat(storeRepository.existsById(EMPTY_ID)).isFalse();
        assertThat(storeRepository.existsById(setupStore.getId())).isTrue();
        assertThat(storeRepository.getById(setupStore.getId())).isEqualTo(setupStore);
    }

    @Test
    void save() {
        //null fields
        store = new Store();
        assertThatThrownBy(()-> storeRepository.save(store)).isInstanceOf(DataAccessException.class);

        //basic save
        store = new Store(null,
                UNIMPORTANT_STRING,
                UNIMPORTANT_NUMBER);
        Long id = storeRepository.save(store).getId();

        //overwrite
        store = new Store(id,
                UNIMPORTANT_STRING,
                NEW_NUMBER);
        Store newStore = storeRepository.save(store);
        assertThat(newStore.getId()).isEqualTo(id);
        assertThat(newStore.getRating()).isEqualTo(NEW_NUMBER);

        //edit and save
        newStore.setRating(ANOTHER_NEW_NUMBER);
        newStore = storeRepository.save(newStore);
        assertThat(newStore.getRating()).isEqualTo(ANOTHER_NEW_NUMBER);
    }
}