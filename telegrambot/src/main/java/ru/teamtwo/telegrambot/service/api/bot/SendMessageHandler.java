package ru.teamtwo.telegrambot.service.api.bot;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface SendMessageHandler {
    void deleteKeyboard(@NonNull String chatId);

    /**
     * Отправляет сообщение в указанный чат. Этот метод также указывает,
     * что нужно удалить меню у пользователя.
     */
    void sendMessage(@NonNull String chatId, @NonNull String text);

    /**
     * Отправляет сообщение в указанный чат с указанным меню.
     */
    void sendMessage(@NonNull String chatId, @NonNull String text, @NonNull ReplyKeyboard replyMarkup);
}
