package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddButtonPressedStage implements StageHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;


    @Override
    public boolean shouldRun(StageContext context) {
        return context.getCustomerState().getStage() == CustomerState.Stage.WAITING_FOR_ADD_OR_FINISH
                && context.getDataCallbackType().equals(TelegramBotInlineMenus.CALLBACK_ORDER_ADD_HEADER);
    }

    @Override
    public void execute(StageContext context) {
        try {
            context.getCustomerState().setCurrentProductId(Integer.valueOf(context.getDataCallbackArgs().get(TelegramBotInlineMenus.CALLBACK_ORDER_ID_PARAM)));
            context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_QUANTITY);

            sendMessageHandler.sendMessage(context.getChatId(), "Введите количество");
        } catch (Exception e) {
            log.error("AddHandler error: {}", e.getMessage());
        }
    }
}
