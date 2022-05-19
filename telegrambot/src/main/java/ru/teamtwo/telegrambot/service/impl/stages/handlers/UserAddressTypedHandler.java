package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.rest.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class UserAddressTypedHandler implements StageHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;
    final RESTHandlerImpl restHandlerImpl;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.customerState().getStage() == Stage.WAITING_FOR_ADDRESS;
    }

    @Override
    public void execute(StageContext context) {
        if (context.message().isEmpty()) return;
        context.customerState().setAddress(context.message());

        sendMessageHandler.sendMessage(context.chatId(), "Заказ добавлен");
        context.customerState().setStage(Stage.WAITING_FOR_SEARCH_START);
    }
}
