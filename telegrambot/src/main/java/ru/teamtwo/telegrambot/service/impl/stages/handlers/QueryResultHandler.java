package ru.teamtwo.telegrambot.service.impl.stages.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.model.bot.menus.TelegramBotMenus;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.api.stage.StageHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

@Component
@RequiredArgsConstructor
public class QueryResultHandler implements StageHandler {
    private final SendMessageHandler sendMessageHandler;

    @Override
    public boolean shouldRun(StageContext context) {
        return context.customerState().getStage() == Stage.WAITING_FOR_SEARCH_QUERY;
    }

    @Override
    public void execute(StageContext context) {
        int resultCount = 1; //TODO:

        if (resultCount == 0) {
            sendMessageHandler.sendMessageDeleteKeyboard(context.chatId(),
                    "По вашему запросу ничего не найдено. Введите другой запрос.");

            context.customerState().setStage(Stage.WAITING_FOR_SEARCH_QUERY);
            return;
        }

        sendMessageHandler.sendMessage(context.chatId(), "Выберите тип сортировки", TelegramBotMenus.getSortByFieldOffsetKeyboard());

        context.customerState().setStage(Stage.WAITING_FOR_SORTING_TYPE_FIELD);
        context.customerState().setSearchQuery(context.message());
    }
}
