package ru.teamtwo.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelegramBotRESTHandler {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotRESTHandler.class);

    @Value("${telegrambot.rest.webClientUri}")
    private static String WEB_CLIENT_URI;
    private static final String PRODUCT_OFFERS_URI = "product-offers";
    private static final String FILTER_PARAMETER = "filter";
    private static final String OFFSET_PARAMETER = "offset";
    private static final String LIMIT_PARAMETER = "limit";
    private static final String ORDER_PARAMETER = "order";
    private final WebClient webClient = WebClient.create(WEB_CLIENT_URI);

    /**
     * Виды сортировки по полям товара для запросов товаров
     */
    public enum OrderType{
        PRICE("price"),
        PRODUCT_RATING("rating"),
        SELLER_RATING("sellerRating");
        public final String text;
        OrderType(String text){
            this.text = text;
        }
    }

    /**
     * Виды сортировки - по убывающей/возрастающей
     */
    public enum OrderTypeAscDesc{
        ASC("asc"),
        DESC("desc");
        public final String text;
        OrderTypeAscDesc(String text){
            this.text = text;
        }
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
        uri.append(PRODUCT_OFFERS_URI);
        uri.append("?");
        uri.append(FILTER_PARAMETER).append("=").append(filter).append("&");
        uri.append(OFFSET_PARAMETER).append("=").append(offset).append("&");
        uri.append(LIMIT_PARAMETER).append("=").append(limit).append("&");
        uri.append(ORDER_PARAMETER).append("=").append(ascDesc).append("_").append(orderType.text);
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
