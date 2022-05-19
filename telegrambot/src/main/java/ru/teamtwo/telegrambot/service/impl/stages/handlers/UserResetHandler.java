package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class UserResetHandler implements StageHandler {

    final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.getMessage().equals("Сбросить пользователя");
    }

    @Override
    public void execute(StageContext context) {
        //context.getCustomerState().reset();
    }
}
