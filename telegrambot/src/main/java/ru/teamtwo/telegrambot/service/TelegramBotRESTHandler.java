package ru.teamtwo.telegrambot.service;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.user.CartItemArrayDto;
import ru.teamtwo.core.dtos.user.CartItemDto;
import ru.teamtwo.core.dtos.user.CustomerDto;
import ru.teamtwo.core.dtos.user.OrderDto;
import ru.teamtwo.core.dtos.user.OrderItemDto;
import ru.teamtwo.core.dtos.ProductDTO;
import ru.teamtwo.telegrambot.client.user.CartItemClient;
import ru.teamtwo.telegrambot.client.user.CustomerClient;
import ru.teamtwo.telegrambot.client.MarketplaceClient;
import ru.teamtwo.telegrambot.client.user.OrderClient;
import ru.teamtwo.telegrambot.client.user.OrderItemClient;
import ru.teamtwo.telegrambot.client.ProductOfferClient;
import ru.teamtwo.telegrambot.model.UserState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class TelegramBotRESTHandler {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotRESTHandler.class);

    @Autowired
    private CartItemClient cartItemClient;
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private MarketplaceClient marketplaceClient;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private OrderItemClient orderItemClient;
    @Autowired
    private ProductOfferClient productOfferClient;

    @Value("${telegrambot.rest.webClientUri}")
    private String WEB_CLIENT_URI;
    private static final String PRODUCT_OFFERS_URI = "/product-offers";
    private static final String FILTER_PARAMETER = "filter";
    private static final String OFFSET_PARAMETER = "offset";
    private static final String LIMIT_PARAMETER = "limit";
    private static final String ORDER_PARAMETER = "order";
    private static final String ORDER_URI = "/order/";
    private static final String ORDER_ITEM_URI = "/order_item/";
    private static final String GET_CART_STATE_URI = "/cart_item/get_cart_state/";
    private static final String POST_CART_STATE_URI = "/cart_item/save_cart_state/";
    private static final String PRODUCT_OFFER_URI = "/product_offer/";
    private static final String CUSTOMER_URI = "/customer/";

    /**
     * Виды сортировки по полям товара для запросов товаров
     */
    public enum OrderType{
        PRODUCT_NAME("Название"),
        PRODUCT_PRICE("Цена"),
        PRODUCT_RATING("Рейтинг"),
        SELLER_RATING("Рейтинг продавца");

        public final String inputName;

        OrderType(String inputName){
            this.inputName = inputName;
        }
    }

    /**
     * Виды сортировки - по убывающей/возрастающей
     */
    public enum OrderTypeAscDesc{
        ASC("По возрастанию"),
        DESC("По убыванию");

        public final String inputName;

        OrderTypeAscDesc(String inputName){
            this.inputName = inputName;
        }
    }

    public void saveCartState(@NonNull UserState userState){
        logger.debug("saveCartState: {}", userState);

        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto();
        cartItemArrayDto.setCartItemDtoList(new HashSet<>());

        userState.getCart().forEach((key, value) -> {
            CartItemDto dto = new CartItemDto();
            dto.setProductId(key);
            dto.setQuantity(value);
            cartItemArrayDto.getCartItemDtoList().add(dto);
        });

        ResponseEntity<?> responseEntity = cartItemClient.saveCartState(Math.toIntExact(userState.getUser().getId()), cartItemArrayDto);

        logger.debug("saveCartState: {}", responseEntity.getStatusCode());
    }

    public Map<Integer, Integer> getCartState(Long userId){
        logger.debug("getCartState: {}", userId);

        Map<Integer, Integer> map = new HashMap<>();

        //чё
        ((CartItemArrayDto) Objects.requireNonNull(cartItemClient.getCartState(Math.toIntExact(userId))
                .getBody()))
                .getCartItemDtoList()
                .forEach(item -> {
                    map.put(item.getProductId(), item.getQuantity());
                });

        logger.debug("getCartState: {}", map.size());

        return map;
    }

    public ProductDTO getProductById(Integer id){
        logger.debug("getProductById: {}", id);

        Map<Integer, Integer> map = new HashMap<>();

        ProductDTO productDTO = productOfferClient.get(id);

        logger.debug("getProductById: {}", productDTO);

        return productDTO;
    }

    /**
     * Отправить POST с новым заказом
     */
    public void postNewOrderFromUserCart(UserState userState){
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(Math.toIntExact(userState.getUser().getId()));
        //orderDto.setCreatedOn(LocalDate.of(2000,10,10));

        Integer newOrderId = Integer.valueOf(Objects.requireNonNull(orderClient.post(orderDto).getBody()).toString());
        logger.debug("postNewOrder - order: {}", newOrderId);

        userState.getCart().entrySet().forEach(entry->{
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setOrderId(newOrderId);
            orderItemDto.setProductOfferId(entry.getKey());
            orderItemDto.setQuantity(entry.getValue());

            Integer itemStatus = Integer.valueOf(Objects.requireNonNull(orderItemClient.post(orderItemDto).getBody()).toString());

            logger.debug("postNewOrder - item: {}", itemStatus);
        });
    }

    public Optional<CustomerDto> getCustomerInfo(UserState userState){
        logger.debug("getCustomerInfo: {}", userState.getUser().getId());
        Optional<CustomerDto> dto = Optional.empty();

        try {
            CustomerDto customerDto = customerClient.get(Math.toIntExact(userState.getUser().getId()));

            logger.debug("getCustomerInfo: {}", customerDto);
            dto = Optional.of(customerDto);
        }catch (Exception e){
            logger.error("getCustomerInfo error: {}", e.getMessage());
        }

        return dto;
    }

    public void updateCustomerInfo(UserState userState){
        logger.debug("updateCustomerInfo: {}", userState.getUser().getId());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(Math.toIntExact(userState.getUser().getId()));
        customerDto.setAddress(userState.getAddress());
        customerDto.setName(userState.getUser().getUserName());

        String status = customerClient.post(customerDto).getBody().toString();
        logger.debug("updateCustomerInfo finished: {}", status);
    }

    /**
     * Запрашивает список товаров через REST API с указанными параметрами.
     * @param filter Текстовый фильтр
     * @param orderType Поле по которому будет сортировка
     * @param ascDesc Сортировка по возрастающей/убывающей
     * @param offset Сдвиг от первого товара в результатах поиска
     * @param limit Максимальное кол-во товаров в листе
     * @return Отсортированный, отфильтрованный список товаров
     */
    public List<ProductDTO> getSortedProductsByFilterWithOffsetAndLimit(String filter, OrderType orderType, OrderTypeAscDesc ascDesc, int offset, int limit){
        logger.debug("getSortedProductsByFilterWithOffset({},{},{},{},{})",filter,orderType,ascDesc,offset,limit);

        List<ProductDTO> productList = marketplaceClient.getProductOffersByProductName(filter, offset, limit, ascDesc+"_"+orderType.toString().replace("_", "."));

        logger.debug("getSortedProductsByFilterWithOffset received DTOs: {}", productList.size());
        productList.forEach(product -> {
            logger.debug("productDTO: id:{} name:{} seller:{}", product.getId(), product.getName(), product.getSellerName());
        });

        return productList;
    }
}
