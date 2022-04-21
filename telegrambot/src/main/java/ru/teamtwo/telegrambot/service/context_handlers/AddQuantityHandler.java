package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.*;

@Component
@RequiredArgsConstructor
public class AddQuantityHandler implements ContextHandler {

    final UserStateHandler userStateHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState() == UserState.State.WAITING_FOR_QUANTITY;
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().getCart().putIfAbsent(userStateHandler.get(context.getUser()).getCurrentProductId(), Integer.valueOf(context.getMessage()));
    }
}
