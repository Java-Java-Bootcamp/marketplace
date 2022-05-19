package ru.teamtwo.telegrambot.service.bot.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.context.ContextHandler;
import ru.teamtwo.telegrambot.service.context.ProcessingContext;
import ru.teamtwo.telegrambot.service.customer.CustomerStateHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateHandlerImpl implements UpdateHandler {
    final CustomerStateHandler customerStateHandler;
    final List<ContextHandler> handlers;

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

        ProcessingContext context = new ProcessingContext(
                update,
                message,
                chatId,
                user,
                customerState,
                "",
                null
        );

        for (ContextHandler handler : handlers) {
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

        ProcessingContext context = new ProcessingContext(
                update,
                "",
                customerState.getChatId(),
                user,
                customerState,
                dataCallbackType,
                dataArgs
        );

        for (ContextHandler handler : handlers) {
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
