package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;

@Component
@RequiredArgsConstructor
public class GoToSearchHandler implements ContextHandler {
    final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState()== CustomerState.State.WAITING_FOR_SEARCH_START
                && context.getMessage().equals("Поиск");
    }

    @Override
    public void execute(ProcessingContext context) {
        sendMessageHandler.sendMessage(context.getChatId(), "Введите запрос");

        context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SEARCH_QUERY);
    }
}
