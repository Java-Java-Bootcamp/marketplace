package ru.teamtwo.telegrambot.service.impl.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.product.ProductSearchHandler;
import ru.teamtwo.telegrambot.service.api.rest.RESTHandler;
import ru.teamtwo.telegrambot.service.api.stage.Stage;
import ru.teamtwo.telegrambot.service.impl.rest.clients.MarketplaceClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CartItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.CustomerClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.customer.OrderItemClient;
import ru.teamtwo.telegrambot.service.impl.rest.clients.product.ProductOfferClient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RESTHandlerImpl implements RESTHandler {
    private final CartItemClient cartItemClient;
    private final CustomerClient customerClient;
    private final MarketplaceClient marketplaceClient;
    private final OrderClient orderClient;
    private final OrderItemClient orderItemClient;
    private final ProductOfferClient productOfferClient;

    @Override
    public CustomerState getCustomerState(Long userId) {
        CustomerDto customerDto = customerClient.get(userId);

        Map<Integer, Integer> cart = new HashMap<>();
        (Objects.requireNonNull(cartItemClient.getCartState(userId)
                .getBody()))
                .cartItemDtoList()
                .forEach(item -> {
                    cart.put(item.productId(), item.quantity());
                });

        Set<OrderDto> orderDtoSet = new HashSet<>();

        CustomerState customerState = CustomerState.builder()
                .address(customerDto.address())
                .chatId(customerDto.chatId())
                .stage(Stage.valueOf(customerDto.state()))
                .searchQuery(customerDto.searchQuery())
                .sortingTypeField(ProductSearchHandler.SortingTypeField.valueOf(customerDto.sortingTypeField()))
                .sortingTypeAscDesc(ProductSearchHandler.SortingTypeAscDesc.valueOf(customerDto.sortingTypeAscDesc()))
                .offset(customerDto.offset())
                .limit(customerDto.limit())
                .cart(cart)
                .currentProductId(customerDto.currentProductId())
                .build();

        return customerState;
    }

    @Override
    public void saveCustomerState(CustomerState customerState) {
        CustomerDto customerDto = new CustomerDto(
                customerState.getUser().getId(),
                customerState.getName(),
                customerState.getAddress(),
                customerState.getChatId(),
                customerState.getStage().toString(),
                customerState.getSearchQuery(),
                customerState.getSortingTypeField().toString(),
                customerState.getSortingTypeAscDesc().toString(),
                customerState.getOffset(),
                customerState.getLimit(),
                customerState.getCurrentProductId()
        );

        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto(
                new HashSet<>()
        );
        customerState.getCart().forEach((key, value) -> {
            CartItemDto dto = new CartItemDto(
                    null,
                    customerState.getUser().getId(),
                    key,
                    value
            );
            cartItemArrayDto.cartItemDtoList().add(dto);
        });
        customerState.getOrderDtoSet().forEach(orderDto -> {
            orderClient.post(orderDto);
        });


        ResponseEntity<?> responseEntity = cartItemClient.saveCartState(customerState.getUser().getId(), cartItemArrayDto);

        String status = customerClient.post(customerDto).getBody().toString();
    }

    @Override
    public Set<ProductDto> queryProducts(ProductSearchHandler.ProductQuery productQuery) {
        Set<ProductDto> productList = marketplaceClient.getProductOffersByProductName(
                productQuery.query(),
                productQuery.offset(),
                productQuery.limit(),
                productQuery.sortingTypeAscDesc()+"_"+productQuery.sortingTypeField().toString().replace("_",".")
        );

        return productList;
    }
}
