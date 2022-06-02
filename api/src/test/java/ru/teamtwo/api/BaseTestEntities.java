package ru.teamtwo.api;

import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.models.product.Product;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.models.product.Store;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.core.dtos.product.StoreDto;

public interface BaseTestEntities {
    Product getProduct();
    ProductOffer getProductOffer();
    Store getStore();
    Customer getCustomer();
    Order getOrder();
    OrderItem getOrderItem();
    CartItem getCartItem();
    ProductDto getProductDto();
    ProductOfferDto getProductOfferDto();
    StoreDto getStoreDto();
    CustomerDto getCustomerDto();
    OrderDto getOrderDto();
    OrderItemDto getOrderItemDto();
    CartItemDto getCartItemDto();
}
