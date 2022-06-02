package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.model.product.Product;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandlerException;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

import java.util.Set;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
@Slf4j
public class SortAscDescHandler implements StageHandler {
    private final CustomerStateHandler customerStateHandler;
    private final SendMessageHandler sendMessageHandler;
    private final RESTHandler restHandler;
    private final TelegramBotInlineMenus inlineMenus;

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
            Set<Product> products = restHandler.queryProducts(productQuery);

            products.forEach(product -> {
                StringJoiner productInfo = new StringJoiner(", ");
                productInfo.add(product.name());
                productInfo.add(product.price().toString()+" рублей");
                productInfo.add(product.rating().toString()+" очков");
                productInfo.add("от "+product.seller());
                productInfo.add(product.sellerRating().toString()+" очков");

                sendMessageHandler.sendMessage(context.chatId(), productInfo.toString(), inlineMenus.createAddButton(String.valueOf(product.productOfferDto().id())));
            });

            context.customerState().setStage(Stage.WAITING_FOR_ADD_OR_FINISH);
        } catch (RESTHandlerException e) {
            log.error("{} {} : {}", this.getClass().getSimpleName(), e.getClass().getSimpleName(), e.getMessage());
            sendMessageHandler.sendMessageDeleteKeyboard(context.chatId(), "");
        }
    }
}