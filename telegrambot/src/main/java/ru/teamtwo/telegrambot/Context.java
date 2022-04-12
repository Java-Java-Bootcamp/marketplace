package ru.teamtwo.telegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Context {

    Map<Long, ContextEnum> buyerChatContext = new ConcurrentHashMap<>();

    public enum ContextEnum {
        START("start"),
        MAIN("main"),
        SEARCH("search");
        public final String text;
        ContextEnum(String text) {
            this.text = text;
        }
    }

    public void setBuyerChatContext(Update update, ContextEnum contextEnum) {
        Long buyerChatId = update.getMessage().getChatId();
        buyerChatContext.put(buyerChatId, contextEnum);
    }

    public String getBuyerChatContext(Update update) {
        Long buyerChatId = update.getMessage().getChatId();
        String context = buyerChatContext.get(buyerChatId).text;
        return context;
    }
}
