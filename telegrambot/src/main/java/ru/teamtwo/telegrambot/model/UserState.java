package ru.teamtwo.telegrambot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.core.dtos.ProductDTO;
import ru.teamtwo.telegrambot.service.SortingType;
import ru.teamtwo.telegrambot.service.SortingTypeAscDesc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.teamtwo.telegrambot.service.SortingType.PRODUCT_RATING;
import static ru.teamtwo.telegrambot.service.SortingTypeAscDesc.ASC;

/**
 * Хранит в себе информацию о состоянии
 * взаимодействия с пользователем: на какой стадии поиска
 * он находится и т.д.
 */
@Data
@NoArgsConstructor
public class UserState {

    private int DEFAULT_OFFSET=0;
    private int DEFAULT_LIMIT=5;

    public enum State{
        WAITING_FOR_SEARCH_START,
        WAITING_FOR_SEARCH_QUERY,
        WAITING_FOR_SORTING_TYPE_FIELD,
        WAITING_FOR_SORTING_TYPE_ASCDESC,
        WAITING_FOR_ADD_OR_FINISH,
        WAITING_FOR_QUANTITY,
        WAITING_FOR_ADDRESS
    }

    private String address = "";
    private User user;
    private String chatId = "";
    private State state = State.WAITING_FOR_SEARCH_START;
    private String searchQuery = "";
    private SortingType sortingType = PRODUCT_RATING;
    private SortingTypeAscDesc sortingTypeAscDesc = ASC;
    private int offset = DEFAULT_OFFSET;
    private int limit = DEFAULT_LIMIT;
    private Map<Integer, Integer> cart = new HashMap<>();
    private Integer currentProductId;
    private List<ProductDTO> queryResult = new ArrayList<>();

    /**
     * Сбрасывает все до стандартных значений
     */
    public void reset(){
        this.setState(State.WAITING_FOR_SEARCH_START);
        this.setSearchQuery("");
        this.getCart().clear();
        this.setOffset(DEFAULT_OFFSET);
        this.setLimit(DEFAULT_LIMIT);
        this.setCurrentProductId(null);
        this.getQueryResult().clear();
        this.setAddress("");
    }
}
