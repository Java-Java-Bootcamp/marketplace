package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
import ru.teamtwo.telegrambot.service.customer.CustomerStateHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddQuantityHandler implements ContextHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState() == CustomerState.State.WAITING_FOR_QUANTITY;
    }

    @Override
    public void execute(ProcessingContext context) {
        log.debug("AddQuantityHandler: {}", context.getMessage());
        try {
            Integer quantity = Integer.valueOf(context.getMessage());

            context.getCustomerState().getCart().put(context.getCustomerState().getCurrentProductId(), quantity);
            context.getCustomerState().setState(CustomerState.State.WAITING_FOR_ADD_OR_FINISH);
            sendMessageHandler.sendMessage(context.getChatId(),"Товар добавлен в корзину. Введите 'Поиск', чтобы сделать новый запрос, или 'Оформить заказ', чтобы закончить");
        } catch (NumberFormatException e) {
            log.error("AddQuantityHandler number error: {}, {}", context.getMessage(), e.getMessage());
            sendMessageHandler.sendMessage(context.getChatId(), "Попробуйте снова");
        }

    }
}
