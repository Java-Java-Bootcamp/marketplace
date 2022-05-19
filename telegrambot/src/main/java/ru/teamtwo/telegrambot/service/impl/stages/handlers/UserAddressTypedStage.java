package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.impl.rest.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class UserAddressTypedStage implements StageHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;
    final RESTHandlerImpl restHandlerImpl;
    @Override
    public boolean shouldRun(StageContext context) {
        return context.getCustomerState().getStage() == CustomerState.Stage.WAITING_FOR_ADDRESS;
    }

    @Override
    public void execute(StageContext context) {
        if (context.getMessage().isEmpty()) return;
        context.getCustomerState().setAddress(context.getMessage());

        sendMessageHandler.sendMessage(context.getChatId(), "Заказ добавлен");
        context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_SEARCH_START);
    }
}
