package ru.teamtwo.telegrambot.model.bot.menus;

import org.springframework.stereotype.Component;
import ru.teamtwo.telegrambot.service.impl.bot.menus.TelegramBotInlineMenu;

/**
 * Хранит темплейты менюшек, отображающихся под постом
 */
@Component
public class TelegramBotInlineMenus {

    public static final String MENU_ORDER_DETAILS_TEXT = "Заказать";
    public static final String MENU_ORDER_ADD_TEXT = "Добавить в корзину";
    public static final String MENU_ORDER_FINISH_TEXT = "Оформить заказ";
    public static final String MENU_ORDER_ADDRESS_TEXT = "Подтвердить";

    public static final String CALLBACK_ORDER_DETAILS_HEADER = "CallbackOrderDetails";
    public static final String CALLBACK_ORDER_ADD_HEADER = "CallbackOrderAdd";
    public static final String CALLBACK_ORDER_FINISH_HEADER = "CallbackOrderFinish";
    public static final String CALLBACK_ORDER_ADDRESS_HEADER = "CallbackOrderAddress";

    public static final String CALLBACK_ORDER_ID_PARAM = "Id";

    public TelegramBotInlineMenu createButton(String text, String callback, String productId){
        return TelegramBotInlineMenu.createMenuWithOneButton(text,
                callback+
                        "," +
                        CALLBACK_ORDER_ID_PARAM+"="+productId);
    }

    /**
     * Менюшка под постом с кратким описанием товара
     */
    public TelegramBotInlineMenu createDetailsButton(String productId){
        return createButton(MENU_ORDER_DETAILS_TEXT, CALLBACK_ORDER_DETAILS_HEADER, productId);
    }

    /**
     * Менюшка под постом
     * с детальным описанием товара, под которым была нажата кнопка "заказать"
     */
    public TelegramBotInlineMenu createAddButton(String productId){
        return createButton(MENU_ORDER_ADD_TEXT, CALLBACK_ORDER_ADD_HEADER, productId);
    }

    /**
     * Менюшка под постом
     * запрашивающим подтверждение указанного количества товара,
     * предполагающая, что это последний товар
     */
    public TelegramBotInlineMenu createFinishButton(String productId){
        return createButton(MENU_ORDER_FINISH_TEXT, CALLBACK_ORDER_ADD_HEADER, productId);
    }

    /**
     * Менюшка под постом, подтверждающим указанный адрес
     */
    public TelegramBotInlineMenu createAddressOkButton(String productId){
        return createButton(MENU_ORDER_ADDRESS_TEXT, CALLBACK_ORDER_ADDRESS_HEADER, productId);
    }
}
