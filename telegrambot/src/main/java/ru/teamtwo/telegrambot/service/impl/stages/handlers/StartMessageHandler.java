package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotMenus;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class StartMessageHandler implements StageHandler {
    private final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.message().equals("/start");
    }

    @Override
    public void execute(StageContext context) {
        sendMessageHandler.sendMessage(context.chatId(), "Добро пожаловать в Маркетплейс!", TelegramBotMenus.getMainMenuKeyboard());

        context.customerState().setStage(Stage.WAITING_FOR_SEARCH_START);
    }
}
