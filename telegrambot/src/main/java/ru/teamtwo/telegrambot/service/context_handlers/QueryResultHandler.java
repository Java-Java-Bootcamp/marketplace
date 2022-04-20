package ru.teamtwo.telegrambot.service.context_handlers;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;


import java.util.List;

@Component
public class QueryResultHandler implements ContextHandler {

    @Override
    public boolean shouldRun(ProcessingContext context) {
        List<String> queryResult = context.getQueryResultHandler().getSearchResult(context.getMessage(), context.getUser());
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SEARCH_QUERY &&
                queryResult != null;
    }

    @Override
    public void execute(ProcessingContext context) {
        List<String> queryResultList = context.getQueryResultHandler().getSearchResult(context.getMessage(), context.getUser());
        for (String product : queryResultList) {
            context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(), product);
        }
        context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(), "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());

        context.getUserState().setState(UserState.State.WAITING_FOR_SORTING_TYPE_FIELD);
        context.getUserState().setSearchQuery(context.getMessage());
    }
}
