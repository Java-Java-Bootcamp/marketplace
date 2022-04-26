package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;

@Component
@RequiredArgsConstructor
public class StartMessageHandler implements ContextHandler {

    final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getMessage().equals("/start");
    }

    @Override
    public void execute(ProcessingContext context) {
        sendMessageHandler.sendMessage(context.getChatId(), "Добро пожаловать в Маркетплейс!",
                TelegramBotMenus.getMainMenuKeyboard());

        context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SEARCH_START);
    }
}
