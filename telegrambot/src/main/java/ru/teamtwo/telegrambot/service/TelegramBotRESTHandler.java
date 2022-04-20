package ru.teamtwo.telegrambot.service;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.teamtwo.core.dtos.user.CartItemArrayDto;
import ru.teamtwo.core.dtos.user.CartItemDto;
import ru.teamtwo.core.dtos.user.CustomerDto;
import ru.teamtwo.core.dtos.user.OrderDto;
import ru.teamtwo.core.dtos.user.OrderItemDto;
import ru.teamtwo.core.dtos.ProductDTO;
import ru.teamtwo.telegrambot.model.UserState;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TelegramBotRESTHandler {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotRESTHandler.class);
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
    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.create(WEB_CLIENT_URI);
    }

    /**
     * Виды сортировки по полям товара для запросов товаров
     */
    public enum OrderType{
        PRODUCT_NAME,
        PRODUCT_PRICE,
        PRODUCT_RATING,
        SELLER_RATING
    }

    /**
     * Виды сортировки - по убывающей/возрастающей
     */
    public enum OrderTypeAscDesc{
        ASC,
        DESC
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

        String stringMono = webClient
                .post()
                .uri(POST_CART_STATE_URI+userState.getUser().getId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(cartItemArrayDto), CartItemArrayDto.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        logger.debug("saveCartState: {}", stringMono);
    }

    public Map<Integer, Integer> getCartState(Long userId){
        logger.debug("getCartState: {}", userId);

        Map<Integer, Integer> map = new HashMap<>();
        Objects.requireNonNull(webClient
                        .get()
                        .uri(GET_CART_STATE_URI + userId)
                        .retrieve()
                        .bodyToMono(CartItemArrayDto.class)
                        .block())
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
        ProductDTO productDTO = webClient
                .get()
                .uri(PRODUCT_OFFER_URI + id)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();

        logger.debug("getProductById: {}", productDTO);

        return productDTO;
    }

    /**
     * Отправить POST с новым заказом
     */
    public void postNewOrderFromUserCart(UserState userState){
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(Math.toIntExact(userState.getUser().getId()));
        orderDto.setCreatedOn(LocalDate.of(2000,10,10));

        Integer newOrderId = webClient
                .post()
                .uri(ORDER_URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(orderDto), OrderDto.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        logger.debug("postNewOrder - order: {}", newOrderId);

        userState.getCart().entrySet().forEach(entry->{
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setOrderId(newOrderId);
            orderItemDto.setProductOfferId(entry.getKey());
            orderItemDto.setQuantity(entry.getValue());

            String itemStatus = webClient
                    .post()
                    .uri(ORDER_ITEM_URI)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(orderItemDto), OrderItemDto.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            logger.debug("postNewOrder - item: {}", itemStatus);
        });
    }

    public CustomerDto getCustomerInfo(UserState userState){
        logger.debug("getCustomerInfo: {}", userState.getUser().getId());

        CustomerDto customerDto = Objects.requireNonNull(webClient
                .get()
                .uri(CUSTOMER_URI + userState.getUser().getId())
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .block());

        logger.debug("getCustomerInfo: {}", customerDto);

        userState.setAddress(customerDto.getAddress());

        return customerDto;
    }

    public void updateCustomerInfo(UserState userState){
        logger.debug("updateCustomerInfo: {}", userState.getUser().getId());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(Math.toIntExact(userState.getUser().getId()));
        customerDto.setAddress(userState.getAddress());
        customerDto.setName(userState.getUser().getUserName());

        String status = webClient
                .post()
                .uri(CUSTOMER_URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(customerDto), CustomerDto.class)
                .retrieve()
                .bodyToMono(String.class).block();
        logger.debug("updateCustomerInfo: {}", status);
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

        StringBuilder uri = new StringBuilder();
        uri.append(WEB_CLIENT_URI);
        uri.append(PRODUCT_OFFERS_URI);
        uri.append("?");
        uri.append(FILTER_PARAMETER).append("=").append(filter).append("&");
        uri.append(OFFSET_PARAMETER).append("=").append(offset).append("&");
        uri.append(LIMIT_PARAMETER).append("=").append(limit).append("&");
        uri.append(ORDER_PARAMETER).append("=").append(ascDesc).append("_")
                .append(orderType.toString().replace("_", "."));
        logger.debug("getSortedProductsByFilterWithOffset URI:{}", uri);

        List<ProductDTO> productList = webClient
                .get()
                .uri(uri.toString())
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .toStream().collect(Collectors.toList());
        logger.debug("getSortedProductsByFilterWithOffset received DTOs: {}", productList.size());
        productList.forEach(product -> {
            logger.debug("productDTO: id:{} name:{} seller:{}", product.getId(), product.getName(), product.getSellerName());
        });

        return productList;
    }
}
