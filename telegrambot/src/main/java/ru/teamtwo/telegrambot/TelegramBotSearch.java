package ru.teamtwo.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.List;

@Component
public class TelegramBotSearch {

    @Autowired
    TelegramBotRESTHandler restHandler;

    @Autowired
    BuyerSearchParams buyerSearchParams;

    @Autowired
    SearchQueryResultHandler searchQueryResultHandler;

        public SendMessage search(Update update) {
            String filter = update.getMessage().getText();

            int offset = 0;
            int limit = 20;


            List<ProductDTO> searchResult = restHandler.getSortedProductsByFilterWithOffsetAndLimit(
                    filter,
                    buyerSearchParams.getBuyerOrderType(update),
                    buyerSearchParams.getBuyerOrderTypeAscDesc(update),
                    offset,
                    limit
                    );

            SendMessage message = searchQueryResultHandler.handler(update, searchResult);
            return message;
        }
}
