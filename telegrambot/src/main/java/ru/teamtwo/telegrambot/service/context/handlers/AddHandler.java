package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.model.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
import ru.teamtwo.telegrambot.service.customer.CustomerStateHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddHandler implements ContextHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;


    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState() == CustomerState.State.WAITING_FOR_ADD_OR_FINISH
                && context.getDataCallbackType().equals(TelegramBotInlineMenus.CALLBACK_ORDER_ADD_HEADER);
    }

    @Override
    public void execute(ProcessingContext context) {
        try {
            context.getCustomerState().setCurrentProductId(Integer.valueOf(context.getDataCallbackArgs().get(TelegramBotInlineMenus.CALLBACK_ORDER_ID_PARAM)));
            context.getCustomerState().setState(CustomerState.State.WAITING_FOR_QUANTITY);

            sendMessageHandler.sendMessage(context.getChatId(), "Введите количество");
        } catch (Exception e) {
            log.error("AddHandler error: {}", e.getMessage());
        }
    }
}
