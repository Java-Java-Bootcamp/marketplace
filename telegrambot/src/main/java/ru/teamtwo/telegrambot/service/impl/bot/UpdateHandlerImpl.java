package ru.teamtwo.telegrambot.service.impl.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.bot.UpdateHandler;
import ru.teamtwo.telegrambot.service.api.context.StageHandler;
import ru.teamtwo.telegrambot.service.api.customer.CustomerStateHandler;
import ru.teamtwo.telegrambot.service.impl.stages.StageContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateHandlerImpl implements UpdateHandler {
    final CustomerStateHandler customerStateHandler;
    final List<StageHandler> handlers;

    private boolean isTextMessage(Update update){
        return update.hasMessage() && update.getMessage().hasText();
    }

    private boolean isCallbackQuery(Update update){
        return update.hasCallbackQuery() && !update.getCallbackQuery().getData().isEmpty();
    }

    private void handleTextMessage(Update update){
        String message = update.getMessage().getText();
        String chatId = String.valueOf(update.getMessage().getChatId());
        User user = update.getMessage().getFrom();
        CustomerState customerState = customerStateHandler.get(user);
        customerState.setChatId(chatId);

        StageContext context = new StageContext(
                update,
                message,
                chatId,
                user,
                customerState,
                "",
                null
        );

        for (StageHandler handler : handlers) {
            if (handler.shouldRun(context)) {
                handler.execute(context);
                break;
            }
        }

        customerStateHandler.save(customerState);
    }

    private void handleCallbackQuery(Update update){
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        User user = callbackQuery.getFrom();
        CustomerState customerState = customerStateHandler.get(user);

        String dataCallbackType = data.substring(0, data.indexOf(","));
        String dataArgsString = data.substring(data.indexOf(",")+1, data.length());
        String[] dataArgsSplit = dataArgsString.split(",");
        Map<String, String> dataArgs = new HashMap<>();
        Arrays.stream(dataArgsSplit).forEach(asplit -> {
            String[] split1 = asplit.split("=");
            dataArgs.put(split1[0], split1[1]);
        });

        StageContext context = new StageContext(
                update,
                "",
                customerState.getChatId(),
                user,
                customerState,
                dataCallbackType,
                dataArgs
        );

        for (StageHandler handler : handlers) {
            if (handler.shouldRun(context)) {
                handler.execute(context);
                break;
            }
        }

        customerStateHandler.save(customerState);
    }

    public void handle(Update update) {
        if (isTextMessage(update)) {
            handleTextMessage(update);
        }else if(isCallbackQuery(update)){
            handleCallbackQuery(update);
        }
    }
}
