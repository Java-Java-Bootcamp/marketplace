package ru.teamtwo.telegrambot.service.bot.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.telegrambot.client.MarketplaceClient;
import ru.teamtwo.telegrambot.client.customer.CartItemClient;
import ru.teamtwo.telegrambot.client.customer.CustomerClient;
import ru.teamtwo.telegrambot.client.customer.OrderClient;
import ru.teamtwo.telegrambot.client.customer.OrderItemClient;
import ru.teamtwo.telegrambot.client.product.ProductOfferClient;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.model.sorting.SortingTypeAscDesc;
import ru.teamtwo.telegrambot.model.sorting.SortingTypeField;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RESTHandlerImpl implements RESTHandler{
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
                .state(CustomerState.State.valueOf(customerDto.state()))
                .searchQuery(customerDto.searchQuery())
                .sortingTypeField(SortingTypeField.valueOf(customerDto.sortingTypeField()))
                .sortingTypeAscDesc(SortingTypeAscDesc.valueOf(customerDto.sortingTypeAscDesc()))
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
                customerState.getState().toString(),
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
    public List<ProductDto> queryProducts(ProductQuery productQuery) {
        List<ProductDto> productList = marketplaceClient.getProductOffersByProductName(
                productQuery.query(),
                productQuery.offset(),
                productQuery.limit(),
                productQuery.sortingTypeAscDesc()+"_"+productQuery.sortingTypeField().toString().replace("_",".")
        );

        return productList;
    }
}
