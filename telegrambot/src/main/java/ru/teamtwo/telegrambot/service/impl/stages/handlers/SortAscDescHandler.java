package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.api.product.ProductSearchHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SortAscDescHandler implements StageHandler {

    final CustomerStateHandler customerStateHandler;
    final SendMessageHandler sendMessageHandler;
    final RESTHandler restHandler;
    final TelegramBotInlineMenus inlineMenus;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.getCustomerState().getStage()== CustomerState.Stage.WAITING_FOR_SORTING_TYPE_FIELD;
    }

    @Override
    public void execute(StageContext context) {
        for(ProductSearchHandler.SortingTypeAscDesc type : ProductSearchHandler.SortingTypeAscDesc.values()){
            if(type.inputName.equals(context.getMessage())){
                context.getCustomerState().setSortingTypeAscDesc(type);
            }
        }
        if(context.getCustomerState().getSortingTypeAscDesc() == null) return;

        ProductSearchHandler.ProductQuery productQuery = new ProductSearchHandler.ProductQuery(
                context.getCustomerState().getSearchQuery(),
                context.getCustomerState().getSortingTypeField(),
                context.getCustomerState().getSortingTypeAscDesc(),
                context.getCustomerState().getOffset(),
                context.getCustomerState().getLimit()
        );

        Set<ProductDto> products = restHandler.queryProducts(productQuery);

        products.forEach(productDTO -> {
            sendMessageHandler.sendMessage(context.getChatId(), productDTO.toString(), inlineMenus.createAddButton(String.valueOf(productDTO.id())));
        });

        context.getCustomerState().setStage(CustomerState.Stage.WAITING_FOR_ADD_OR_FINISH);
    }
}