package ru.teamtwo.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.List;

import static ru.teamtwo.telegrambot.menus.TelegramBotMenus.getSortByFieldKeyboard;

@Component
public class SearchQueryHandler {

    @Autowired
    TelegramBotRESTHandler restHandler;

    @Autowired
    UserStateHandler userStateHandler;

    public List<ProductDTO> getSearchResult(String message, User user) {
        TelegramBotRESTHandler.OrderType orderType = userStateHandler.get(user).getOrderType();
        TelegramBotRESTHandler.OrderTypeAscDesc orderTypeAscDesc = userStateHandler.get(user).getOrderTypeAscDesc();
        int offset = 0;
        int limit = 5;

        List<ProductDTO> queryResult = restHandler.getSortedProductsByFilterWithOffsetAndLimit(message, orderType, orderTypeAscDesc, offset, limit);
        return queryResult;
    }
}
