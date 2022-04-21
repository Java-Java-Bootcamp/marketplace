package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.configuration.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;

@Component
@RequiredArgsConstructor
public class GoToMenuHandler implements ContextHandler {
    final TelegramBotSendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()==UserState.State.WAITING_FOR_SEARCH_QUERY
                && context.getMessage().equals("Главное меню");
    }

    @Override
    public void execute(ProcessingContext context) {
        sendMessageHandler.sendMessage(context.getChatId(), "Вы в главном меню",
                TelegramBotMenus.getMainMenuKeyboard());

        context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_START);
    }
}
