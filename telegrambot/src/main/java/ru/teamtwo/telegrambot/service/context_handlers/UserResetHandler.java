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
public class UserResetHandler implements ContextHandler {

    final TelegramBotSendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getMessage().equals("Сбросить пользователя");
    }

    @Override
    public void execute(ProcessingContext context) {
        context.getUserState().reset();
    }
}
