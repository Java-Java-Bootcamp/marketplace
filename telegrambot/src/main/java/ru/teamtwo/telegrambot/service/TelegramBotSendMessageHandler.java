package ru.teamtwo.telegrambot.service;


import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Содержит методы для отправки пользователю SendMessage с меню и без.
 */
@Component
public class TelegramBotSendMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotSendMessageHandler.class);

    @Lazy
    @Autowired
    TelegramBot telegramBot;
    /**
     * Отправляет сообщение в указанный чат. Этот метод также указывает,
     * что нужно удалить меню у пользователя.
     * @param bot Бот
     * @param chatId ID чата
     * @param text Текст сообщения
     */
    public void sendMessage(@NonNull String chatId, @NonNull String text){
        sendMessage(chatId, text, new ReplyKeyboardRemove(true));
    }

    /**
     * Отправляет сообщение в указанный чат с указанным меню.
     * @param bot Бот
     * @param chatId ID чата
     * @param text Текст сообщения
     * @param replyMarkup Меню/клавиатура
     */
    public void sendMessage(@NonNull String chatId, @NonNull String text, @NonNull ReplyKeyboard replyMarkup){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyMarkup);

        logger.debug("Отправляю сообщение {}: {}",chatId,text);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Не смог отправить сообщение {}, {}, {}: {}",chatId,text,replyMarkup.toString(),e.getMessage());

            e.printStackTrace();
        }
    }

}
