package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.api.product.ProductSearchHandler;
import ru.teamtwo.telegrambot.service.impl.rest.RESTHandlerImpl;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class SortFieldHandler implements StageHandler {

    final CustomerStateHandler customerStateHandler;
    final SendMessageHandler sendMessageHandler;
    final RESTHandlerImpl restHandlerImpl;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.getCustomerState().getStage()== CustomerState.Stage.WAITING_FOR_SORTING_TYPE_FIELD;
    }

    @Override
    public void execute(StageContext context) {
        for(ProductSearchHandler.SortingTypeField type : ProductSearchHandler.SortingTypeField.values()){
            if(type.inputName.equals(context.getMessage())){
                context.getCustomerState().setSortingTypeField(type);
            }
        }
        if(context.getCustomerState().getSortingTypeField() == null) return;

        sendMessageHandler.sendMessage( context.getChatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
        context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_SORTING_TYPE_ASCDESC);
    }
}
