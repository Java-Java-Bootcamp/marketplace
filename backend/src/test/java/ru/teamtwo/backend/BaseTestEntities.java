package ru.teamtwo.backend;

import ru.teamtwo.backend.models.customer.CartItem;
import ru.teamtwo.backend.models.customer.Customer;
import ru.teamtwo.backend.models.customer.Order;
import ru.teamtwo.backend.models.customer.OrderItem;
import ru.teamtwo.backend.models.product.Product;
import ru.teamtwo.backend.models.product.ProductOffer;
import ru.teamtwo.backend.models.product.Store;
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
