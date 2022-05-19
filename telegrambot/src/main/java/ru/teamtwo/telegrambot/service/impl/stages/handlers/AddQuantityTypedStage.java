package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddQuantityTypedStage implements StageHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.getCustomerState().getStage() == CustomerState.Stage.WAITING_FOR_QUANTITY;
    }

    @Override
    public void execute(StageContext context) {
        log.debug("AddQuantityHandler: {}", context.getMessage());
        try {
            Integer quantity = Integer.valueOf(context.getMessage());

            context.getCustomerState().getCart().put(context.getCustomerState().getCurrentProductId(), quantity);
            context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_ADD_OR_FINISH);
            sendMessageHandler.sendMessage(context.getChatId(),"Товар добавлен в корзину. Введите 'Поиск', чтобы сделать новый запрос, или 'Оформить заказ', чтобы закончить");
        } catch (NumberFormatException e) {
            log.error("AddQuantityHandler number error: {}, {}", context.getMessage(), e.getMessage());
            sendMessageHandler.sendMessage(context.getChatId(), "Попробуйте снова");
        }

    }
}
