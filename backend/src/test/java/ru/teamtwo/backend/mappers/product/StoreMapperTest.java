package ru.teamtwo.backend.mappers.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.backend.BaseTestEntitiesImpl;
import ru.teamtwo.backend.models.product.Store;
import ru.teamtwo.core.dtos.product.StoreDto;

import static ru.teamtwo.backend.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class StoreMapperTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Autowired
    StoreMapper storeMapper;
    Store store;
    StoreDto storeDto;

    @BeforeEach
    void setUp() {
        store = baseTestEntities.getStore();
        storeDto = new StoreDto(store.getId(),
                store.getName(),
                store.getRating()
        );
    }

    @Test
    void convertToDto() {
        StoreDto convert = storeMapper.convertToDto(store);
        assertDtoAndEntityEqual(convert, store);
    }

    @Test
    void convertFromDto() {
        Store convert = storeMapper.convertToEntity(storeDto);
        assertDtoAndEntityEqual(storeDto, convert);
    }
}