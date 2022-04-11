package ru.teamtwo.telegrambot;

/**
 * Хранит темплейты менюшек, отображающихся под постом
 */
public class TelegramBotInlineMenus {

    public static final String CALLBACK_ORDER_DETAILS_HEADER = "CallbackOrderDetails";
    public static final String CALLBACK_ORDER_ACCEPT_HEADER = "CallbackOrderAccept";
    public static final String CALLBACK_ORDER_ID_PARAM = "Id";

    /**
     * Менюшка с кнопкой "заказать", отображающаяся под постом с кратким описанием товара
     * @param productId ID товара
     * @return Меню для setReplyMarkup
     */
    public TelegramBotInlineMenu createOrderButton(String productId){
        return TelegramBotInlineMenu.createMenuWithOneButton("Заказать",
                CALLBACK_ORDER_DETAILS_HEADER+
                        "," +
                        CALLBACK_ORDER_ID_PARAM+"="+productId);
    }

    /**
     * Менюшка с кнопкой "принять", отображающаяся под постом
     * с детальным описанием товара, под которым была нажата кнопка "заказать"
     * @param productId ID товара
     * @return Меню для setReplyMarkup
     */
    public TelegramBotInlineMenu createAcceptButton(String productId){
        return TelegramBotInlineMenu.createMenuWithOneButton("Подтвердить",
                CALLBACK_ORDER_ACCEPT_HEADER+
                        ","+
                        CALLBACK_ORDER_ID_PARAM+"="+productId);
    }
}
