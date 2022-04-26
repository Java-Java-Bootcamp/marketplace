package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
import ru.teamtwo.telegrambot.model.sorting.SortingType;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandler;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.customer.CustomerStateHandler;

@Component
@RequiredArgsConstructor
public class SortFieldHandler implements ContextHandler {

    final CustomerStateHandler customerStateHandler;
    final SendMessageHandler sendMessageHandler;
    final RESTHandler restHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState()== CustomerState.State.WAITING_FOR_SORTING_TYPE_FIELD;
    }

    @Override
    public void execute(ProcessingContext context) {
        for(SortingType type : SortingType.values()){
            if(type.inputName.equals(context.getMessage())){
                context.getCustomerState().setSortingType(type);
            }
        }
        if(context.getCustomerState().getSortingType() == null) return;

        sendMessageHandler.sendMessage( context.getChatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
        context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
    }
}
