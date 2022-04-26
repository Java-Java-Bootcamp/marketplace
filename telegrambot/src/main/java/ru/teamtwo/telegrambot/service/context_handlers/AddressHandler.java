package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.ProductDTO;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;
import ru.teamtwo.telegrambot.service.UserStateHandler;

@Component
@RequiredArgsConstructor
public class AddressHandler implements ContextHandler {
    final TelegramBotSendMessageHandler sendMessageHandler;
    final UserStateHandler userStateHandler;
    final TelegramBotRESTHandler restHandler;
    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState() == UserState.State.WAITING_FOR_ADDRESS;
    }

    @Override
    public void execute(ProcessingContext context) {
        if(context.getMessage().isEmpty()) return;
        context.getUserState().setAddress(context.getMessage());

        restHandler.postNewOrderFromUserCart(context.getUserState());

        StringBuilder b = new StringBuilder("Ваш заказ добавлен\n");
        context.getUserState().getCart().forEach((s, integer) -> {
            ProductDTO product = restHandler.getProductDTOById(Integer.valueOf(s));
            b.append(product.toString());
            b.append("\n");
        });
        b.append("Введите 'Поиск', чтобы сделать новый запрос");

       sendMessageHandler.sendMessage(context.getChatId(), b.toString());
       context.getUserState().setState(UserState.State.WAITING_FOR_SEARCH_START);
    }
}
