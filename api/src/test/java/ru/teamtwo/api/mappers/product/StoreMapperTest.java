package ru.teamtwo.api.mappers.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntities;
import ru.teamtwo.api.models.product.Store;
import ru.teamtwo.core.dtos.product.StoreDto;

import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class StoreMapperTest {
    @Autowired
    BaseTestEntities baseTestEntities;
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
        StoreDto convert = storeMapper.convert(store);
        assertDtoAndEntityEqual(convert, store);
    }

    @Test
    void convertFromDto() {
        Store convert = storeMapper.convert(storeDto);
        assertDtoAndEntityEqual(storeDto, convert);
    }
}