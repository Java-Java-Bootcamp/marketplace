package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SortByProductRatingHandler implements ContextHandler {

    final UserStateHandler userStateHandler;
    final TelegramBotSendMessageHandler sendMessageHandler;
    final TelegramBotSearchQueryHandler queryResultHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SORTING_TYPE_FIELD
                && context.getUpdate().getMessage().getText().equals("Рейтинг товара");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().setOrderType(TelegramBotRESTHandler.OrderType.PRODUCT_PRICE);
        List<String> queryResultList = queryResultHandler.getSearchResult(userStateHandler.get(context.getUser()).getSearchQuery(), context.getUser());
        for (String product : queryResultList) {
            sendMessageHandler.sendMessage(context.getBot(), context.getChatId(), product);
        }
        sendMessageHandler.sendMessage(context.getBot(), context.getChatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
        context.getUserState().setState(UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
    }
}
