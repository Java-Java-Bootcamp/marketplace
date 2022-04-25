package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.model.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;
import ru.teamtwo.telegrambot.service.UserStateHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddHandler implements ContextHandler {
    final TelegramBotSendMessageHandler sendMessageHandler;
    final UserStateHandler userStateHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState() == UserState.State.WAITING_FOR_ADD_OR_FINISH
                && context.getDataCallbackType().equals(TelegramBotInlineMenus.CALLBACK_ORDER_ADD_HEADER);
    }

    @Override
    public void execute(ProcessingContext context) {
        try {
            context.getUserState().setCurrentProductId(Integer.valueOf(context.getDataCallbackArgs().get(TelegramBotInlineMenus.CALLBACK_ORDER_ID_PARAM)));
            context.getUserState().setState(UserState.State.WAITING_FOR_QUANTITY);

            sendMessageHandler.sendMessage(context.getChatId(), "Введите количество");
        } catch (Exception e){
            log.error("AddHandler error: {}", e.getMessage());
        }
    }
}
