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
public class EmptyQueryResultHandler implements ContextHandler {

    final TelegramBotSendMessageHandler sendMessageHandler;
    final TelegramBotSearchQueryHandler queryResultHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        List<String> queryResult = queryResultHandler.getSearchResult(context.getMessage(), context.getUser());
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SEARCH_QUERY &&
                queryResult == null;
    }

    @Override
    public void execute(ProcessingContext context) {
        sendMessageHandler.sendMessage(context.getBot(), context.getChatId(),
                    "По вашему запросу ничего не найдено \n" +
                         "Попробуйте другой запрос",
                TelegramBotMenus.getSearchMenu());

        context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
    }
}
