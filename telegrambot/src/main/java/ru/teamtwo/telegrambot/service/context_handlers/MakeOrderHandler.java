package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;

@Component
@RequiredArgsConstructor
public class MakeOrderHandler implements ContextHandler {
    final TelegramBotSendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_ADD_OR_FINISH
                && context.getUpdate().getMessage().getText().equals("Оформить заказ")
                && !context.getUserState().getCart().isEmpty();
    }

    @Override
    public void execute(ProcessingContext context) {
        sendMessageHandler.sendMessage(context.getChatId(),"Введите адрес");

        context.getUserState().setState(UserState.State.WAITING_FOR_ADDRESS);
    }
}
