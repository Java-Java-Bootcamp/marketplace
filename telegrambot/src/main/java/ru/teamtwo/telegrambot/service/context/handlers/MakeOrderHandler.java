package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;

@Component
@RequiredArgsConstructor
public class MakeOrderHandler implements ContextHandler {
    final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState()== CustomerState.State.WAITING_FOR_ADD_OR_FINISH
                && context.getUpdate().getMessage().getText().equals("Оформить заказ")
                && !context.getCustomerState().getCart().isEmpty();
    }

    @Override
    public void execute(ProcessingContext context) {
        sendMessageHandler.sendMessage(context.getChatId(),"Введите адрес");

        context.getCustomerState().setState(CustomerState.State.WAITING_FOR_ADDRESS);
    }
}
