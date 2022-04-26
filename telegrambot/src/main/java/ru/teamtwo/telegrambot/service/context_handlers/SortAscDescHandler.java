package ru.teamtwo.telegrambot.service.context_handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.ProductDTO;
import ru.teamtwo.telegrambot.model.UserState;
import ru.teamtwo.telegrambot.model.menus.TelegramBotInlineMenus;
import ru.teamtwo.telegrambot.service.ContextHandler;
import ru.teamtwo.telegrambot.service.ProcessingContext;
import ru.teamtwo.telegrambot.service.SortingTypeAscDesc;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;
import ru.teamtwo.telegrambot.service.TelegramBotSendMessageHandler;
import ru.teamtwo.telegrambot.service.UserStateHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SortAscDescHandler implements ContextHandler {

    final UserStateHandler userStateHandler;
    final TelegramBotSendMessageHandler sendMessageHandler;
    final TelegramBotRESTHandler restHandler;
    final TelegramBotInlineMenus inlineMenus;

    @Override
    public boolean shouldRun(ProcessingContext context) {
        return context.getUserState().getState()== UserState.State.WAITING_FOR_SORTING_TYPE_FIELD;
    }

    @Override
    public void execute(ProcessingContext context) {
        for(SortingTypeAscDesc type : SortingTypeAscDesc.values()){
            if(type.inputName.equals(context.getMessage())){
                context.getUserState().setSortingTypeAscDesc(type);
            }
        }
        if(context.getUserState().getSortingTypeAscDesc() == null) return;

        List<ProductDTO> products = restHandler.getSortedProductsByFilterWithOffsetAndLimit(
                context.getUserState().getSearchQuery(),
                context.getUserState().getSortingType(),
                context.getUserState().getSortingTypeAscDesc(),
                context.getUserState().getOffset(),
                context.getUserState().getLimit());

        products.forEach(productDTO -> {
            sendMessageHandler.sendMessage(context.getChatId(), productDTO.toString(), inlineMenus.createAddButton(String.valueOf(productDTO.getId())));
        });

        context.getUserState().setState(UserState.State.WAITING_FOR_ADD_OR_FINISH);
    }
}