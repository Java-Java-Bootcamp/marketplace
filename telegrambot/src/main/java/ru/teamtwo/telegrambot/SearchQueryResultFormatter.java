package ru.teamtwo.telegrambot;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchQueryResultFormatter {

    public List<String> getFormattedResult(List<ProductDTO> queryResult) {
        List<String> ProductDTOString = new ArrayList<>();
        for (ProductDTO product : queryResult) {
            ProductDTOString.add(product.toString());
        }
        return ProductDTOString;
    }
}
