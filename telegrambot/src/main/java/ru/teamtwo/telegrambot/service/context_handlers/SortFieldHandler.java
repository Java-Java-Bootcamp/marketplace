package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;
import ru.teamtwo.telegrambot.service.TelegramBotSearchQueryHandler;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;
import ru.teamtwo.telegrambot.service.UserStateHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SortFieldHandler implements ContextHandler {

    final UserStateHandler userStateHandler;
    final TelegramBotSendMessageHandler sendMessageHandler;
    final TelegramBotRESTHandler restHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_SORTING_TYPE_FIELD;
    }

    @Override
    public void execute(ProcessingContext context) {
        for(TelegramBotRESTHandler.OrderType type : TelegramBotRESTHandler.OrderType.values()){
            if(type.inputName.equals(context.getMessage())){
                context.getUserState().setOrderType(type);
            }
        }
        if(context.getUserState().getOrderType() == null) return;

        sendMessageHandler.sendMessage( context.getChatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
        context.getUserState().setState(UserState.State.WAITING_FOR_SORTING_TYPE_ASCDESC);
    }
}
