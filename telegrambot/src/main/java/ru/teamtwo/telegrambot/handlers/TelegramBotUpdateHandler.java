package ru.teamtwo.telegrambot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.dtos.ProductDTO;
import ru.teamtwo.telegrambot.menus.TelegramBotMenus;

import java.util.List;


@Component
public class TelegramBotUpdateHandler {
    @Autowired
    UserStateHandler userStateHandler;
    @Autowired
    TelegramBotSendMessageHandler sendMessageHandler;

    @Autowired
    SearchQueryHandler queryResultHandler;

    public void handle(TelegramLongPollingBot bot,  Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            User user = update.getMessage().getFrom();

            if(message.equals("/start")){
                sendMessageHandler.sendMessage(bot, chatId, "Добро пожаловать в Маркетплейс!",
                        TelegramBotMenus.getMainMenuKeyboard());

                userStateHandler.get(user).setState(UserState.State.WAITING_FOR_SEARCH_START);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SEARCH_START
                    && message.equals("Поиск")) {
                sendMessageHandler.sendMessage(bot, chatId, "Введите запрос",
                        TelegramBotMenus.getSearchMenu());

                userStateHandler.get(user).setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SEARCH_QUERY
                    && message.equals("Главное меню")) {
                sendMessageHandler.sendMessage(bot, chatId, "Вы в главном меню", TelegramBotMenus.getMainMenuKeyboard());

                userStateHandler.get(user).setState(UserState.State.WAITING_FOR_SEARCH_START);
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SEARCH_QUERY) {
                List<ProductDTO> queryResult = queryResultHandler.getSearchResult(message, user);
                if (queryResult.isEmpty()) {
                    sendMessageHandler.sendMessage(bot, chatId, "По вашему запросу ничего не найдено" +
                                                                     "Попробуйте другой запрос",
                            TelegramBotMenus.getSearchMenu());

                    userStateHandler.get(user).setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
                }
                else {
                    String queryResultText = queryResultHandler.queryResultToString(queryResult);
                    sendMessageHandler.sendMessage(bot, chatId, queryResultText);
                    sendMessageHandler.sendMessage(bot, chatId, "Выберите тип сортировки", TelegramBotMenus.getSortByFieldKeyboard());

                    userStateHandler.get(user).setState(UserState.State.WAITING_FOR_SORTING_TYPE_FIELD);
                    userStateHandler.get(user).setSearchQuery(message);
                }
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_FIELD) {
                sendMessageHandler.sendMessage(bot, chatId, "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescKeyboard());

                userStateHandler.get(user).setState(UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
                //userStateHandler.get(user).setOrderType();
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC) {
                //Вывести товары
                //userStateHandler.get(user).setOrderTypeAscDesc();
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_ADD_OR_FINISH) {
                if(message.equals("Оформить заказ")){
                    sendMessageHandler.sendMessage(bot,chatId,"Введите адрес");
                }
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_QUANTITY) {
                userStateHandler.get(user).getCart().putIfAbsent(userStateHandler.get(user).getCurrentProductId(), Integer.valueOf(message));
            }
            else if (userStateHandler.get(user).getState()==UserState.State.WAITING_FOR_ADDRESS) {
            }
        }
    }
}
