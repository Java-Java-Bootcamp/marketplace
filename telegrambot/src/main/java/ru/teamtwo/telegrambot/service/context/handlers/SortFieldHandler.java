package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.model.sorting.SortingTypeField;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandler;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
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
        for(SortingTypeField type : SortingTypeField.values()){
            if(type.inputName.equals(context.getMessage())){
                context.getCustomerState().setSortingTypeField(type);
            }
        }
        if(context.getCustomerState().getSortingTypeField() == null) return;

        sendMessageHandler.sendMessage( context.getChatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
        context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
    }
}
