package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SortAscHandler implements ContextHandler {
    final UserStateHandler userStateHandler;
    final TelegramBotSendMessageHandler sendMessageHandler;
    final TelegramBotSearchQueryHandler queryResultHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC
                && context.getUpdate().getMessage().getText().equals("По возрастанию");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().setOrderTypeAscDesc(TelegramBotRESTHandler.OrderTypeAscDesc.ASC);
        List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(context.getUser()).getSearchQuery(), context.getUser());
        for (String product : queryResultList) {
            sendMessageHandler.sendMessage(context.getBot(), context.getChatId(), product);
        }
        sendMessageHandler.sendMessage(context.getBot(), context.getChatId(), "Результат");
    }
}
