package ru.teamtwo.core.dtos.controller.product;

import ru.teamtwo.core.dtos.controller.BaseController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import java.util.Set;

public interface ProductOfferController extends BaseController<ProductOfferDto> {
    record ProductQuery(
            String query,
            SortingTypeField sortingTypeField,
            SortingTypeAscDesc sortingTypeAscDesc,
            Integer offset,
            Integer limit
    ) {}

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

    Set<ProductOfferDto> query(ProductQuery productQuery);
}
