package ru.teamtwo.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.SearchQueryResultFormatter;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchQueryHandler {

    @Autowired
    TelegramBotRESTHandler restHandler;

    @Autowired
    UserStateHandler userStateHandler;

    @Autowired
    SearchQueryResultFormatter resultFormatter;

    public List<String> getSearchResult(String message, User user) {
        TelegramBotRESTHandler.OrderType orderType = userStateHandler.get(user).getOrderType();
        TelegramBotRESTHandler.OrderTypeAscDesc orderTypeAscDesc = userStateHandler.get(user).getOrderTypeAscDesc();
        int offset = 0;
        int limit = 10;

        List<ProductDTO> queryResult = restHandler.getSortedProductsByFilterWithOffsetAndLimit(message, orderType, orderTypeAscDesc, offset, limit);
        if (queryResult.isEmpty()) {
            return null;
        }
        else {
            List<String> ProductDTOToString = resultFormatter.getFormattedResult(queryResult);
            return ProductDTOToString;
        }
    }
}
