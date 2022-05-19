package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddQuantityTypedHandler implements StageHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.customerState().getStage() == Stage.WAITING_FOR_QUANTITY;
    }

    @Override
    public void execute(StageContext context) {
        log.debug("AddQuantityHandler: {}", context.message());
        try {
            Integer quantity = Integer.valueOf(context.message());

            context.customerState().getCart().put(context.customerState().getCurrentProductId(), quantity);
            context.customerState().setStage(Stage.WAITING_FOR_ADD_OR_FINISH);
            sendMessageHandler.sendMessage(context.chatId(), "Товар добавлен в корзину. Введите 'Поиск', чтобы сделать новый запрос, или 'Оформить заказ', чтобы закончить");
        } catch (NumberFormatException e) {
            log.error("AddQuantityHandler number error: {}, {}", context.message(), e.getMessage());
            sendMessageHandler.sendMessage(context.chatId(), "Попробуйте снова");
        }

    }
}
