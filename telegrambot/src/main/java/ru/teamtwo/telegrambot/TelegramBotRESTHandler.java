package ru.teamtwo.telegrambot;

import org.glassfish.jersey.internal.util.Producer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import ru.teamtwo.telegrambot.dtos.ProductDTO;

import java.util.List;
import java.util.stream.Stream;

@Component
public class TelegramBotRESTHandler {

    private static final String WEB_CLIENT_URI = "http://localhost:8080";
    private static final String PRODUCT_OFFERS_URI = "product-offers";

    private final WebClient webClient = WebClient.create(WEB_CLIENT_URI);

    public Stream<ProductDTO> getProductsByFilter(String filter){
        return webClient
                .get()
                .uri(PRODUCT_OFFERS_URI+"?filter="+filter)
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .toStream();
    }
}
