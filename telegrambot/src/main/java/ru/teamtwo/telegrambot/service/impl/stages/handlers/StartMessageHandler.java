package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class StartMessageHandler implements StageHandler {

    final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.getMessage().equals("/start");
    }

    @Override
    public void execute(StageContext context) {
        sendMessageHandler.sendMessage(context.getChatId(), "Добро пожаловать в Маркетплейс!",
                TelegramBotMenus.getMainMenuKeyboard());

        context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_SEARCH_START);
    }
}
