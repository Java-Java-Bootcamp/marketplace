package ru.teamtwo.telegrambot.service.context_handlers;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;

import java.util.List;

@Component
public class EmptyQueryResultHandler implements ContextHandler {

    @Override
    public boolean shouldRun(ProcessingContext context) {
        List<String> queryResult = context.getQueryResultHandler().getSearchResult(context.getMessage(), context.getUser());
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SEARCH_QUERY &&
                queryResult == null;
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(),
                    "По вашему запросу ничего не найдено \n" +
                        "Попробуйте другой запрос",
                TelegramBotMenus.getSearchMenu());

        context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_QUERY);
    }
}
