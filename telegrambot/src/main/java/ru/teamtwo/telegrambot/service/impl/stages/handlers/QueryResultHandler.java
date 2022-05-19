package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.impl.rest.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class QueryResultHandler implements StageHandler {

    final SendMessageHandler sendMessageHandler;
    final RESTHandlerImpl restHandlerImpl;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.getCustomerState().getStage()== CustomerState.Stage.WAITING_FOR_SEARCH_QUERY;
    }

    @Override
    public void execute(StageContext context) {
        int resultCount = 1; //TODO:

        if(resultCount == 0){
            sendMessageHandler.sendMessage(context.getChatId(),
                    "По вашему запросу ничего не найдено. Введите другой запрос.");

            context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_SEARCH_QUERY);
            return;
        }

        sendMessageHandler.sendMessage(context.getChatId(), "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());

        context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_SORTING_TYPE_FIELD);
        context.getCustomerState().setSearchQuery(context.getMessage());
    }
}
