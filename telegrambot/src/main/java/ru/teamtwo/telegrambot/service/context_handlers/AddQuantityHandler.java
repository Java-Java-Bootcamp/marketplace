package ru.teamtwo.telegrambot.service.context_handlers;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;

@Component
public class AddQuantityHandler implements ContextHandler {

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_QUANTITY;
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().getCart().putIfAbsent(context.getUserStateHandler().get(context.getUser()).getCurrentProductId(), Integer.valueOf(context.getMessage()));
    }
}
