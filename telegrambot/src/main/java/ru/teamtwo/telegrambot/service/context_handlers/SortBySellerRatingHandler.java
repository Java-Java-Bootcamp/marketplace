package ru.teamtwo.telegrambot.service.context_handlers;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;

import java.util.List;

@Component
public class SortBySellerRatingHandler implements ContextHandler {

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_SORTING_TYPE_FIELD
                && context.getUpdate().getMessage().getText().equals("Рейтинг продавца");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().setOrderType(TelegramBotRESTHandler.OrderType.PRODUCT_PRICE);
        List<String> queryResultList = context.getQueryResultHandler().getSearchResult(context.getUserStateHandler().get(context.getUser()).getSearchQuery(), context.getUser());
        for (String product : queryResultList) {
            context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(), product);
        }
        context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
        context.getUserState().setState(UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
    }
}

