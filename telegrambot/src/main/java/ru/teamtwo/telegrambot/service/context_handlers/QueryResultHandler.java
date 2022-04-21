package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotSearchQueryHandler;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;


import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryResultHandler implements ContextHandler {

    final TelegramBotSendMessageHandler sendMessageHandler;
    final Tel queryResultHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SEARCH_QUERY;
    }

    @Override
    public void execute(ProcessingContext context) {
        List<String> queryResult = queryResultHandler.getSearchResult(context.getMessage(),     context.getUser());

        if(queryResult.isEmpty()){
            sendMessageHandler.sendMessage(context.getChatId(),
                    "По вашему запросу ничего не найдено. Введите другой запрос.");

            context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
            return;
        }

        context.getUserState().setQueryResult(queryResult);
        sendMessageHandler.sendMessage(context.getChatId(), "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());

        context.getUserState().setState(UserState.State.WAITING_FOR_SORTING_TYPE_FIELD);
        context.getUserState().setSearchQuery(context.getMessage());
    }
}
