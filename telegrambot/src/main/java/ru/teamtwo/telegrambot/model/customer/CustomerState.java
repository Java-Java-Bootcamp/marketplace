package ru.teamtwo.telegrambot.model.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.service.api.stage.Stage;

import java.util.Set;

/**
 * Хранит в себе информацию о состоянии
 * взаимодействия с пользователем: на какой стадии поиска
 * он находится и т.д.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerState {
    private String address;
    private String name;
    private User user;
    private String chatId;
    private Stage stage;
    private String searchQuery;
    private ProductOfferController.SortingTypeField sortingTypeField;
    private ProductOfferController.SortingTypeAscDesc sortingTypeAscDesc;
    private int offset;
    private int limit;
    private Set<CartItemDto> cart;
    private Long currentProductId;
    private Set<ProductDto> queryResult;
    private Set<CustomerOrder> orders;
}
