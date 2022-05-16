package ru.teamtwo.telegrambot.service.context.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.bot.handlers.RESTHandler;
import ru.teamtwo.telegrambot.service.bot.handlers.SendMessageHandler;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
import ru.teamtwo.telegrambot.service.customer.CustomerStateHandler;

@Component
@RequiredArgsConstructor
public class AddressHandler implements ContextHandler {
    final SendMessageHandler sendMessageHandler;
    final CustomerStateHandler customerStateHandler;
    final RESTHandler restHandler;
    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getCustomerState().getState() == CustomerState.State.WAITING_FOR_ADDRESS;
    }

    @Override
    public void execute(ProcessingContext context) {
        if(context.getMessage().isEmpty()) return;
        context.getCustomerState().setAddress(context.getMessage());

        restHandler.postNewOrderFromUserCart(context.getCustomerState());

        StringBuilder b = new StringBuilder("Ваш заказ добавлен\n");
        context.getCustomerState().getCart().forEach((s, integer) -> {
            ProductDto product = restHandler.getProductDTOById(Integer.valueOf(s));
            b.append(product.toString());
            b.append("\n");
        });
        b.append("Введите 'Поиск', чтобы сделать новый запрос");

       sendMessageHandler.sendMessage(context.getChatId(), b.toString());
       context.getCustomerState().setState(CustomerState.State.WAITING_FOR_SEARCH_START);
    }
}
