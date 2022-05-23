package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class SortAscDescHandler implements StageHandler {

    final CustomerStateHandler customerStateHandler;
    final SendMessageHandler sendMessageHandler;
    final RESTHandler restHandler;
    final TelegramBotInlineMenus inlineMenus;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.customerState().getStage() == Stage.WAITING_FOR_SORTING_TYPE_FIELD;
    }

    @Override
    public void execute(StageContext context) {
        for (ProductOfferController.SortingTypeAscDesc type : ProductOfferController.SortingTypeAscDesc.values()) {
            if (type.inputName.equals(context.message())) {
                context.customerState().setSortingTypeAscDesc(type);
            }
        }
        if (context.customerState().getSortingTypeAscDesc() == null) return;

        ProductOfferController.ProductQuery productQuery = new ProductOfferController.ProductQuery(
                context.customerState().getSearchQuery(),
                context.customerState().getSortingTypeField(),
                context.customerState().getSortingTypeAscDesc(),
                context.customerState().getOffset(),
                context.customerState().getLimit()
        );

        try {
            Set<ProductOfferDto> products = restHandler.queryProducts(productQuery);

            products.forEach(productDTO -> {
                sendMessageHandler.sendMessage(context.chatId(), productDTO.toString(), inlineMenus.createAddButton(String.valueOf(productDTO.id())));
            });

            context.customerState().setStage(Stage.WAITING_FOR_ADD_OR_FINISH);
        } catch (RESTHandlerException e) {
            log.error("{} {} : {}", this.getClass().getSimpleName(), e.getClass().getSimpleName(), e.getMessage());
            sendMessageHandler.sendMessageDeleteKeyboard(context.chatId(), "");
        }
    }
}