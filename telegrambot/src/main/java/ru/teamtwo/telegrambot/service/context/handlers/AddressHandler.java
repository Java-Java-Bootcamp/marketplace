package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
import ru.teamtwo.telegrambot.service.customer.CustomerStateHandler;

@Component
@RequiredArgsConstructor
public class AddressHandler implements ContextHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;
    final RESTHandlerImpl restHandlerImpl;
    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState() == CustomerState.State.WAITING_FOR_ADDRESS;
    }

    @Override
    public void execute(ProcessingContext context) {
        if (context.getMessage().isEmpty()) return;
        context.getCustomerState().setAddress(context.getMessage());

        sendMessageHandler.sendMessage(context.getChatId(), "Заказ добавлен");
        context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SEARCH_START);
    }
}
