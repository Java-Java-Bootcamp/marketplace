package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;

@Component
@RequiredArgsConstructor
public class UserResetHandler implements ContextHandler {

    final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getMessage().equals("Сбросить пользователя");
    }

    @Override
    public void execute(ProcessingContext context) {
        //context.getCustomerState().reset();
    }
}
