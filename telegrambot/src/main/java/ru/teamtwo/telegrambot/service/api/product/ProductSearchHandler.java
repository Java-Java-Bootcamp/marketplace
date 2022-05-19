package ru.teamtwo.telegrambot.service.api.product;

import ru.teamtwo.core.dtos.product.ProductDto;

import java.util.Set;

public interface ProductSearchHandler {
    Set<ProductDto> search(ProductQuery productQuery);

    record ProductQuery(
        String query,
        SortingTypeField sortingTypeField,
        SortingTypeAscDesc sortingTypeAscDesc,
        Integer offset,
        Integer limit
    ) {}

    /**
     * Виды сортировки - по убывающей/возрастающей.
     */
    enum SortingTypeAscDesc {
        ASC("По возрастанию"),
        DESC("По убыванию");

        public final String inputName;

        SortingTypeAscDesc(String inputName) {
            this.inputName = inputName;
        }
    }

    /**
     * Виды сортировки по полям товара.
     */
    enum SortingTypeField {
        PRODUCT_NAME("Название"),
        PRODUCT_PRICE("Цена"),
        PRODUCT_RATING("Рейтинг"),
        SELLER_RATING("Рейтинг продавца");

        public final String inputName;

        SortingTypeField(String inputName) {
            this.inputName = inputName;
        }
    }
}
