package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;
import ru.teamtwo.telegrambot.service.TelegramBotSearchQueryHandler;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;


import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryResultHandler implements ContextHandler {

    final TelegramBotSendMessageHandler sendMessageHandler;
    final TelegramBotRESTHandler restHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SEARCH_QUERY;
    }

    @Override
    public void execute(ProcessingContext context) {
        int resultCount = 1; //TODO:

        if(resultCount == 0){
            sendMessageHandler.sendMessage(context.getChatId(),
                    "По вашему запросу ничего не найдено. Введите другой запрос.");

            context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
            return;
        }

        sendMessageHandler.sendMessage(context.getChatId(), "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());

        context.getUserState().setState(UserState.State.WAITING_FOR_SORTING_TYPE_FIELD);
        context.getUserState().setSearchQuery(context.getMessage());
    }
}
