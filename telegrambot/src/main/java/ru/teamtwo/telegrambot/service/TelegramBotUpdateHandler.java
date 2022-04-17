package ru.teamtwo.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;

import java.util.List;


@Component
public class TelegramBotUpdateHandler {
    @Autowired
    UserStateHandler userStateHandler;
    @Autowired
    TelegramBotSendMessageHandler sendMessageHandler;

    @Autowired
    TelegramBotSearchQueryHandler queryResultHandler;

    public void handle(TelegramLongPollingBot bot,  Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            User user = update.getMessage().getFrom();
            var userState = userStateHandler.get(user);

            if(message.equals("/start")){
                sendMessageHandler.sendMessage(bot, chatId, "Добро пожаловать в Маркетплейс!",
                        TelegramBotMenus.getMainMenuKeyboard());

                userState.setState(UserState.State.WAITING_FOR_SEARCH_START);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SEARCH_START
                    && message.equals("Поиск")) {
                sendMessageHandler.sendMessage(bot, chatId, "Введите запрос",
                        TelegramBotMenus.getSearchMenu());

                userState.setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SEARCH_QUERY
                    && message.equals("Главное меню")) {
                sendMessageHandler.sendMessage(bot, chatId, "Вы в главном меню", TelegramBotMenus.getMainMenuKeyboard());

                userState.setState(UserState.State.WAITING_FOR_SEARCH_START);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SEARCH_QUERY) {
                List<String> queryResult = queryResultHandler.getSearchResult(message, user);
                if (queryResult == null) {
                    sendMessageHandler.sendMessage(bot, chatId, "По вашему запросу ничего не найдено \n" +
                                                                     "Попробуйте другой запрос",
                            TelegramBotMenus.getSearchMenu());

                    userState.setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
                }
                else {
                    List<String> queryResultList = queryResultHandler.getSearchResult(message, user);
                    for (String product : queryResultList) {
                        sendMessageHandler.sendMessage(bot, chatId, product);
                    }
                    sendMessageHandler.sendMessage(bot, chatId, "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());

                    userState.setState(UserState.State.WAITING_FOR_SORTING_TYPE_FIELD);
                    userState.setSearchQuery(message);
                }
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_FIELD
            && update.getMessage().getText().equals("Цена")) {
                userState.setOrderType(TelegramBotRESTHandler.OrderType.PRODUCT_PRICE);
                List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(user).getSearchQuery(), user);
                for (String product : queryResultList) {
                    sendMessageHandler.sendMessage(bot, chatId, product);
                }
                sendMessageHandler.sendMessage(bot, chatId, "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
                userState.setState(UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_FIELD
                    && update.getMessage().getText().equals("Рейтинг товара")) {
                userState.setOrderType(TelegramBotRESTHandler.OrderType.PRODUCT_RATING);
                List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(user).getSearchQuery(), user);
                for (String product : queryResultList) {
                    sendMessageHandler.sendMessage(bot, chatId, product);
                }
                sendMessageHandler.sendMessage(bot, chatId, "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
                userState.setState(UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_FIELD
                    && update.getMessage().getText().equals("Рейтинг продавца")) {
                userState.setOrderType(TelegramBotRESTHandler.OrderType.SELLER_RATING);
                List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(user).getSearchQuery(), user);
                for (String product : queryResultList) {
                    sendMessageHandler.sendMessage(bot, chatId, product);
                }
                sendMessageHandler.sendMessage(bot, chatId, "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
                userState.setState(UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC
                && update.getMessage().getText().equals("По возрастанию")) {
                userState.setOrderTypeAscDesc(TelegramBotRESTHandler.OrderTypeAscDesc.ASC);
                List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(user).getSearchQuery(), user);
                for (String product : queryResultList) {
                    sendMessageHandler.sendMessage(bot, chatId, product);
                }
                    sendMessageHandler.sendMessage(bot, chatId, "Результат");
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC
                    && update.getMessage().getText().equals("По убыванию")) {
                userState.setOrderTypeAscDesc(TelegramBotRESTHandler.OrderTypeAscDesc.DESC);
                List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(user).getSearchQuery(), user);
                for (String product : queryResultList) {
                    sendMessageHandler.sendMessage(bot, chatId, product);
                }
                sendMessageHandler.sendMessage(bot, chatId, "Результат");
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_FIELD
                    && update.getMessage().getText().equals("Показать ещё")) {
                userState.setOffset(userStateHandler.get(user).getOffset()+1);
                List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(user).getSearchQuery(), user);
                for (String product : queryResultList) {
                    sendMessageHandler.sendMessage(bot, chatId, product);
                }
                sendMessageHandler.sendMessage(bot, chatId, "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC
                    && update.getMessage().getText().equals("Показать ещё")) {
                userState.setOffset(userStateHandler.get(user).getOffset()+1);
                List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(user).getSearchQuery(), user);
                for (String product : queryResultList) {
                    sendMessageHandler.sendMessage(bot, chatId, product);
                }
                sendMessageHandler.sendMessage(bot, chatId, "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_ADD_OR_FINISH) {
                if(message.equals("Оформить заказ")){
                    sendMessageHandler.sendMessage(bot,chatId,"Введите адрес");
                }
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_QUANTITY) {
                userState.getCart().putIfAbsent(userStateHandler.get(user).getCurrentProductId(), Integer.valueOf(message));
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_ADDRESS) {
            }
        }
    }
}
