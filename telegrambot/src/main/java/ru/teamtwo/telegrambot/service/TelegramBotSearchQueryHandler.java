package ru.teamtwo.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.SearchQueryResultFormatter;
import ru.teamtwo.telegrambot.dtos.ProductDTO;
import ru.teamtwo.telegrambot.model.UserState;

import java.util.List;

@Component
public class TelegramBotSearchQueryHandler {

    @Autowired
    TelegramBotRESTHandler restHandler;

    @Autowired
    UserStateHandler userStateHandler;

    @Autowired
    SearchQueryResultFormatter resultFormatter;

    public List<String> getSearchResult(String message, User user) {
        TelegramBotRESTHandler.OrderType orderType = userStateHandler.get(user).getOrderType();
        TelegramBotRESTHandler.OrderTypeAscDesc orderTypeAscDesc = userStateHandler.get(user).getOrderTypeAscDesc();
        int offset = userStateHandler.get(user).getOffset();
        int limit = userStateHandler.get(user).getLimit();

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
