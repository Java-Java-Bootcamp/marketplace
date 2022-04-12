package ru.teamtwo.telegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.List;

import static ru.teamtwo.telegrambot.menus.TelegramBotMenus.getSortByFieldKeyboard;

@Component
public class SearchQueryResultHandler {

    public SendMessage handler(Update update, List<ProductDTO> searchResult) {
        SendMessage message = new SendMessage();

        if (searchResult.isEmpty()) {
            message.setText("По вашему запросу ничего не найдено");
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (ProductDTO s : searchResult) {
                sb.append(s);
                sb.append("\t");
            }
            String messageText = sb.toString();
            message.setText(messageText);
//          отдать пользователю меню сортировки
            message.setReplyMarkup(getSortByFieldKeyboard());
//          сохранить сообщение пользователя, чтобы потом отправить текст с новым пармтером сортировки

            }

        message.setChatId(update.getMessage().getChatId().toString());
        return message;
    }
}
