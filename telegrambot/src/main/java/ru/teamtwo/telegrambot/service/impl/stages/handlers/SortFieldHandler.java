package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotMenus;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SortFieldHandler implements StageHandler {
    private final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.customerState().getStage() == Stage.WAITING_FOR_SORTING_TYPE_FIELD;
    }

    @Override
    public void execute(StageContext context) {
        Optional<ProductOfferController.SortingTypeField> sortingTypeField = Arrays.stream(ProductOfferController.SortingTypeField.values()).filter((type) -> type.inputName.equals(context.message())).findFirst();
        if (sortingTypeField.isEmpty()) return;
        context.customerState().setSortingTypeField(sortingTypeField.get());

        sendMessageHandler.sendMessage(context.chatId(), "По убыванию/возрастанию?", TelegramBotMenus.getSortByAscDescOffsetKeyboard());
        context.customerState().setStage(Stage.WAITING_FOR_SORTING_TYPE_ASCDESC);
    }
}
