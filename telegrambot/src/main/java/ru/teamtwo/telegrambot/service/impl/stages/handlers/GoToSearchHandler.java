package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class GoToSearchHandler implements StageHandler {
    private final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.customerState().getStage() == Stage.WAITING_FOR_SEARCH_START
                && context.message().equals("Поиск");
    }

    @Override
    public void execute(StageContext context) {
        sendMessageHandler.sendMessageDeleteKeyboard(context.chatId(), "Введите запрос");

        context.customerState().setStage(Stage.WAITING_FOR_SEARCH_QUERY);
    }
}
