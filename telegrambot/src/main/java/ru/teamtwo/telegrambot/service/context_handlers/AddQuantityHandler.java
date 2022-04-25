package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddQuantityHandler implements ContextHandler {
    final TelegramBotSendMessageHandler sendMessageHandler;
    final UserStateHandler userStateHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState() == UserState.State.WAITING_FOR_QUANTITY;
    }

    @Override
    public void execute(ProcessingContext context) {
        log.debug("AddQuantityHandler: {}", context.getMessage());
        try {
            Integer quantity = Integer.valueOf(context.getMessage());

            context.getUserState().getCart().put(context.getUserState().getCurrentProductId(), quantity);
            context.getUserState().setState(UserState.State.WAITING_FOR_ADD_OR_FINISH);
            sendMessageHandler.sendMessage(context.getChatId(),"Товар добавлен в корзину. Введите 'Поиск', чтобы сделать новый запрос, или 'Оформить заказ', чтобы закончить");
        } catch (NumberFormatException e) {
            log.error("AddQuantityHandler number error: {}, {}", context.getMessage(), e.getMessage());
            sendMessageHandler.sendMessage(context.getChatId(), "Попробуйте снова");
        }

    }
}
