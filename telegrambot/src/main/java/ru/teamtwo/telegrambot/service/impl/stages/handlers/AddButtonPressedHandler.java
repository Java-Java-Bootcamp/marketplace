package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddButtonPressedHandler implements StageHandler {
    private final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.customerState().getStage() == Stage.WAITING_FOR_ADD_OR_FINISH
                && context.dataCallbackType().equals(TelegramBotInlineMenus.CALLBACK_ORDER_ADD_HEADER);
    }

    @Override
    public void execute(StageContext context) {
        try {
            context.customerState().setCurrentProductId(Long.valueOf(context.dataCallbackArgs().get(TelegramBotInlineMenus.CALLBACK_ORDER_ID_PARAM)));
            context.customerState().setStage(Stage.WAITING_FOR_QUANTITY);

            sendMessageHandler.sendMessageDeleteKeyboard(context.chatId(), "Введите количество");
        } catch (Exception e) {
            log.error("AddHandler error: {}", e.getMessage());
        }
    }
}
