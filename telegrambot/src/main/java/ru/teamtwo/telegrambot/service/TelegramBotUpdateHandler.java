package ru.teamtwo.telegrambot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.telegrambot.model.UserState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBotUpdateHandler {
    final UserStateHandler userStateHandler;
    final List<ContextHandler> handlers;
    final TelegramBotRESTHandler restHandler;

    public void handle(Update update) {
        log.debug("Update: {}, {}", update.hasMessage(), update.hasCallbackQuery());
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            User user = update.getMessage().getFrom();
            UserState userState = userStateHandler.get(user);
            userState.setChatId(chatId);

            ProcessingContext context = new ProcessingContext(
                    update,
                    message,
                    chatId,
                    user,
                    userState,
                    "",
                    null
            );

            for (ContextHandler handler : handlers) {
                if (handler.shouldRun(context)) {
                    handler.execute(context);
                    break;
                }
            }

            restHandler.updateCustomerInfo(userState);
        }

        if(update.hasCallbackQuery() && !update.getCallbackQuery().getData().isEmpty()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            User user = callbackQuery.getFrom();
            UserState state = userStateHandler.get(user);

            log.info("Callback query: {}", data);
            String dataCallbackType = data.substring(0, data.indexOf(","));
            String dataArgsString = data.substring(data.indexOf(",")+1, data.length());
            String[] dataArgsSplit = dataArgsString.split(",");
            Map<String, String> dataArgs = new HashMap<>();
            Arrays.stream(dataArgsSplit).forEach(asplit -> {
                String[] split1 = asplit.split("=");
                dataArgs.put(split1[0], split1[1]);
            });

            log.debug("Callback type: {}",dataCallbackType);
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
