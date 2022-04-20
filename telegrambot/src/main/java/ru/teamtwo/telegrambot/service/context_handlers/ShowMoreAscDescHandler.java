package ru.teamtwo.telegrambot.service.context_handlers;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;

import java.util.List;

@Component
public class ShowMoreAscDescHandler implements ContextHandler {

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC
                && context.getUpdate().getMessage().getText().equals("Показать ещё");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().setOffset(context.getUserStateHandler().get(context.getUser()).getOffset()+1);
        List<String> queryResultList = context.getQueryResultHandler().getSearchResult(context.getUserStateHandler().get(context.getUser()).getSearchQuery(), context.getUser());
        for (String product : queryResultList) {
            context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(), product);
        }
        context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
    }
}
