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
public class UpdateHandler {
    final CustomerStateHandler customerStateHandler;
    final List<ContextHandler> handlers;
    final RESTHandler restHandler;

    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
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

            restHandler.updateCustomerInfo(customerState);
        }

        if(update.hasCallbackQuery() && !update.getCallbackQuery().getData().isEmpty()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            User user = callbackQuery.getFrom();
            CustomerState state = customerStateHandler.get(user);

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
                    state.getChatId(),
                    user,
                    state,
                    dataCallbackType,
                    dataArgs
            );

            for (ContextHandler handler : handlers) {
                if (handler.shouldRun(context)) {
                    handler.execute(context);
                    break;
                }
            }

            restHandler.updateCustomerInfo(state);
        }
    }
}
