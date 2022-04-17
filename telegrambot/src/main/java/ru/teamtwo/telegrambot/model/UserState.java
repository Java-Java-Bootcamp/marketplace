package ru.teamtwo.telegrambot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.teamtwo.telegrambot.service.TelegramBotRESTHandler;

import javax.annotation.PostConstruct;
import java.util.Map;

import static ru.teamtwo.telegrambot.service.TelegramBotRESTHandler.OrderType.PRODUCT_RATING;
import static ru.teamtwo.telegrambot.service.TelegramBotRESTHandler.OrderTypeAscDesc.ASC;

/**
 * Хранит в себе информацию о состоянии
 * взаимодействия с пользователем: на какой стадии поиска
 * он находится и т.д.
 */
@Data
@NoArgsConstructor
@Component
public class UserState {

    @Value("${telegrambot.userState.defaultOffset}")
    private int DEFAULT_OFFSET;
    @Value("${telegrambot.userState.defaultLimit}")
    private int DEFAULT_LIMIT;

    public enum State{
        WAITING_FOR_SEARCH_START,
        WAITING_FOR_SEARCH_QUERY,
        WAITING_FOR_SORTING_TYPE_FIELD,
        WAITING_FOR_SORTING_TYPE_ASCDESC,
        WAITING_FOR_ADD_OR_FINISH,
        WAITING_FOR_QUANTITY,
        WAITING_FOR_ADDRESS
    }

    private String chatId = "";
    private State state = State.WAITING_FOR_SEARCH_START;
    private String searchQuery = "";
    private TelegramBotRESTHandler.OrderType orderType = PRODUCT_RATING;
    private TelegramBotRESTHandler.OrderTypeAscDesc orderTypeAscDesc = ASC;
    private int offset;
    private int limit;
    private Map<String, Integer> cart;
    private String currentProductId = "";

    @PostConstruct
    public void init() {
        offset = DEFAULT_OFFSET;
        limit = DEFAULT_LIMIT;
    }

    /**
     * Сбрасывает все до стандартных значений
     */
    public void reset(){
        this.setState(UserState.State.WAITING_FOR_SEARCH_START);
        this.setSearchQuery("");
        this.getCart().clear();
        this.setOffset(DEFAULT_OFFSET);
        this.setLimit(DEFAULT_LIMIT);
        this.setCurrentProductId("");
    }
}
