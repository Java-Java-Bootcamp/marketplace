package ru.teamtwo.telegrambot;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBotRESTHandler {
    ProductDTO dtoSample1 = new ProductDTO(1, "Dish washer", "Bosh",
            "A dishwasher desc", "A dishwasher category", 1.1, 2.2,
            "Dishwasher seller name", 3, 4);

    ProductDTO dtoSample2 = new ProductDTO(2, "TV", "Sony",
            "A TV desc", "A TV category", 5.5, 6.6,
            "TV seller name", 7, 8);

    public enum OrderType{
        PRICE("price"),
        PRODUCT_RATING("rating"),
        SELLER_RATING("sellerRating");
        public final String text;
        OrderType(String text){
            this.text = text;
        }
    }

    /**
     * Виды сортировки - по убывающей/возрастающей
     */
    public enum OrderTypeAscDesc{
        ASC("asc"),
        DESC("desc");
        public final String text;
        OrderTypeAscDesc(String text){
            this.text = text;
        }
    }

    public List<ProductDTO> getSortedProductsByFilterWithOffsetAndLimit(String filter, OrderType orderType, OrderTypeAscDesc ascDesc, int offset, int limit){
        List<ProductDTO> sampleList = new ArrayList<>();
        sampleList.add(dtoSample1);
        sampleList.add(dtoSample2);
        return sampleList;
    }
}
