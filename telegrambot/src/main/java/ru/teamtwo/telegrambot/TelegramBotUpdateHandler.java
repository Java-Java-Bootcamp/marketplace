package ru.teamtwo.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamtwo.telegrambot.menus.TelegramBotMainMenu;
import ru.teamtwo.telegrambot.menus.TelegramBotSearchMenu;

import static ru.teamtwo.telegrambot.Context.ContextEnum.*;
import static ru.teamtwo.telegrambot.TelegramBotRESTHandler.OrderType.PRODUCT_RATING;
import static ru.teamtwo.telegrambot.TelegramBotRESTHandler.OrderTypeAscDesc.DESC;

@Component
public class TelegramBotUpdateHandler {

    @Autowired
    TelegramBotMainMenu telegramBotMainMenu;

    @Autowired
    TelegramBotSearchMenu telegramBotSearchMenu;

    @Autowired
    TelegramBotSearch telegramBotSearch;

    @Autowired
    Context context;

    @Autowired
    BuyerSearchParams buyerSearchParams;

    public SendMessage sendGreetingMessage(Update update) {
        SendMessage greetingMessage = telegramBotMainMenu.mainMenu(update);
        greetingMessage.setText("Добро пожаловать в Маркетплейс! \n" +
                                "Для поиска товара нажмите Поиск");
        context.setBuyerChatContext(update, START);
        return greetingMessage;
    }

    public SendMessage goToSearch(Update update) {
        SendMessage searchMenuMessage = telegramBotSearchMenu.searchMenu(update);
        context.setBuyerChatContext(update, SEARCH);
        buyerSearchParams.setBuyerOrderType(update, PRODUCT_RATING);
        buyerSearchParams.setbuyerOrderTypeAscDesc(update, DESC);
        return searchMenuMessage;
    }

    public SendMessage goToMain(Update update) {
        SendMessage searchMenuMessage = telegramBotMainMenu.mainMenu(update);
        context.setBuyerChatContext(update, MAIN);
        return searchMenuMessage;
    }

    public SendMessage searchProducts(Update update) {
        SendMessage message = telegramBotSearch.search(update);
        return message;
    }
}
