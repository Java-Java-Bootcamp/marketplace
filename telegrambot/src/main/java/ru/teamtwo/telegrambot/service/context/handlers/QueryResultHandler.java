package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;

@Component
@RequiredArgsConstructor
public class QueryResultHandler implements ContextHandler {

    final SendMessageHandler sendMessageHandler;
    final RESTHandlerImpl restHandlerImpl;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState()== CustomerState.State.WAITING_FOR_SEARCH_QUERY;
    }

    @Override
    public void execute(ProcessingContext context) {
        int resultCount = 1; //TODO:

        if(resultCount == 0){
            sendMessageHandler.sendMessage(context.getChatId(),
                    "По вашему запросу ничего не найдено. Введите другой запрос.");

            context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SEARCH_QUERY);
            return;
        }

        sendMessageHandler.sendMessage(context.getChatId(), "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());

        context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SORTING_TYPE_FIELD);
        context.getCustomerState().setSearchQuery(context.getMessage());
    }
}
