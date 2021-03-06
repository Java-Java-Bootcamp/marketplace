package ru.teamtwo.telegrambot.service.impl.bot;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.teamtwo.telegrambot.service.api.bot.SendMessageHandler;

/**
 * Содержит методы для отправки пользователю SendMessage с меню и без.
 */
@Component
@Slf4j
public class SendMessageHandlerImpl implements SendMessageHandler {
    private final TelegramLongPollingBot telegramBot;

    @Autowired
    @Lazy
    SendMessageHandlerImpl(TelegramLongPollingBot telegramBot){
        this.telegramBot = telegramBot;
    }

    public void deleteKeyboard(@NonNull String chatId){
        sendMessage(chatId, "", new ReplyKeyboardRemove(true));
    }

    @Override
    public void sendMessage(@NonNull String chatId, @NonNull String text) {
        sendMessage(chatId, text);
    }

    /**
     * Отправляет сообщение в указанный чат. Этот метод также указывает,
     * что нужно удалить меню у пользователя.
     */
    public void sendMessageDeleteKeyboard(@NonNull String chatId, @NonNull String text){
        sendMessage(chatId, text, new ReplyKeyboardRemove(true));
    }

    /**
     * Отправляет сообщение в указанный чат с указанным меню.
     */
    public void sendMessage(@NonNull String chatId, @NonNull String text, @NonNull ReplyKeyboard replyMarkup){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyMarkup);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Не смог отправить сообщение {}, {}, {}: {}",chatId,text,replyMarkup,e.getMessage());

            e.printStackTrace();
        }
    }

}
