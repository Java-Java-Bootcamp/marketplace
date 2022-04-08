package ru.teamtwo.telegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBotUpdateHandler {

    public String searchProducts(Update update) {
//      форматирование текста из update в строку(пока допускаем что передан
//      только текстовый поисковой запрос)
        String filter = String.valueOf(update.getMessage());

        String searchResult = getSortedProductsByFilterWithOffsetAndLimit(filter);

//      здесь преобразование dto в текст и форматирование текста

        return searchResult;
    }
}
