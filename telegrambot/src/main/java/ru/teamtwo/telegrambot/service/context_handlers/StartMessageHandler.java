package ru.teamtwo.telegrambot.service.context_handlers;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;

@Component
public class StartMessageHandler implements ContextHandler {

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getMessage().equals("/start");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getSendMessageHandler().sendMessage(context.getBot(), context.getChatId(), "Добро пожаловать в Маркетплейс!",
                TelegramBotMenus.getMainMenuKeyboard());

        context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_START);
    }
}
