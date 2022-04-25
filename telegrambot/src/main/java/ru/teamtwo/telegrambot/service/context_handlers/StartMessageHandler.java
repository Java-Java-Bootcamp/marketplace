package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.*;

@Component
@RequiredArgsConstructor
public class StartMessageHandler implements ContextHandler {

    final TelegramBotSendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getMessage().equals("/start");
    }

    @Override
    public void execute(ProcessingContext context) {
        sendMessageHandler.sendMessage(context.getChatId(), "Добро пожаловать в Маркетплейс!",
                TelegramBotMenus.getMainMenuKeyboard());

        context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_START);
    }
}
