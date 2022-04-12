package ru.teamtwo.telegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class BuyerSearchParams {

    Map<Long, TelegramBotRESTHandler.OrderType> buyerOrderType = new HashMap<>();
    Map<Long, TelegramBotRESTHandler.OrderTypeAscDesc> buyerOrderTypeAscDesc = new HashMap<>();

    public void setBuyerOrderType(Update update, TelegramBotRESTHandler.OrderType OrderType) {
        Long buyerChatId = update.getMessage().getChatId();
        buyerOrderType.put(buyerChatId, OrderType);
    }

    public TelegramBotRESTHandler.OrderType getBuyerOrderType(Update update) {
        Long buyerChatId = update.getMessage().getChatId();
        TelegramBotRESTHandler.OrderType orderType = buyerOrderType.get(buyerChatId);
        return orderType;
    }

    public void setBuyerOrderTypeAscDesc(Update update, TelegramBotRESTHandler.OrderTypeAscDesc OrderTypeAscDesc) {
        Long buyerChatId = update.getMessage().getChatId();
        buyerOrderTypeAscDesc.put(buyerChatId, OrderTypeAscDesc);
    }

    public TelegramBotRESTHandler.OrderTypeAscDesc getBuyerOrderTypeAscDesc(Update update) {
        Long buyerChatId = update.getMessage().getChatId();
        TelegramBotRESTHandler.OrderTypeAscDesc orderTypeAscDesc = buyerOrderTypeAscDesc.get(buyerChatId);
        return orderTypeAscDesc;
    }
}