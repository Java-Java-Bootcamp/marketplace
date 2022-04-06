package ru.teamtwo.telegrambot;

import org.glassfish.jersey.internal.util.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TelegramBotRESTHandler {

    private static Logger logger = LoggerFactory.getLogger(TelegramBotRESTHandler.class);

    private static final String WEB_CLIENT_URI = "http://localhost:8080";
    private static final String PRODUCT_OFFERS_URI = "product-offers";
    private static final String FILTER_PARAMETER = "filter";
    private static final String OFFSET_PARAMETER = "offset";
    private static final String ORDER_PARAMETER = "order";

    private final WebClient webClient = WebClient.create(WEB_CLIENT_URI);

    public enum OrderType{
        PRICE("price"),
        PRODUCT_RATING("rating"),
        SELLER_RATING("sellerRating");
        public final String text;
        OrderType(String text){
            this.text = text;
        }
    }

    public enum OrderTypeAscDesc{
        ASC("asc"),
        DESC("desc");
        public final String text;
        OrderTypeAscDesc(String text){
            this.text = text;
        }
    }

    public List<ProductDTO> getSortedProductsByFilterWithOffset(String filter, OrderType orderType, OrderTypeAscDesc ascDesc, int offset){
        logger.debug("getSortedProductsByFilterWithOffset({},{},{},{})",filter,orderType,ascDesc,offset);

        StringBuilder uri = new StringBuilder();
        uri.append(PRODUCT_OFFERS_URI);
        uri.append("?");
        uri.append(FILTER_PARAMETER).append("=").append(filter).append("&");
        uri.append(OFFSET_PARAMETER).append("=").append(offset).append("&");
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
