package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowMoreSortParameterHandler implements ContextHandler {
    final UserStateHandler userStateHandler;
    final TelegramBotSendMessageHandler sendMessageHandler;
    final TelegramBotSearchQueryHandler queryResultHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SORTING_TYPE_FIELD
                && context.getUpdate().getMessage().getText().equals("Показать ещё");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().setOffset(userStateHandler.get(context.getUser()).getOffset()+1);
        List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(context.getUser()).getSearchQuery(), context.getUser());
        for (String product : queryResultList) {
            sendMessageHandler.sendMessage(context.getBot(), context.getChatId(), product);
        }
        sendMessageHandler.sendMessage(context.getBot(), context.getChatId(), "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());
    }
}
