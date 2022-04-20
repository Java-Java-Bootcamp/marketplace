package ru.teamtwo.telegrambot.service.context_handlers;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;

@Component
public class MakeOrderHandler implements ContextHandler {

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_ADD_OR_FINISH
                && context.getUpdate().getMessage().getText().equals("Оформить заказ");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(),"Введите адрес");
    }
}
