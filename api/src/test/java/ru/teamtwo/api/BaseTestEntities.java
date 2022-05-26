package ru.teamtwo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.models.product.Product;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.models.product.Store;
import ru.teamtwo.api.repository.customer.CartItemRepository;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.customer.OrderItemRepository;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.api.repository.product.ProductRepository;
import ru.teamtwo.api.repository.product.StoreRepository;

import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;

@Component
@Transactional
@RequiredArgsConstructor
public class BaseTestEntities {
    public final ProductRepository productRepository;
    public final ProductOfferRepository productOfferRepository;
    public final StoreRepository storeRepository;
    public final CustomerRepository customerRepository;
    public final OrderRepository orderRepository;
    public final OrderItemRepository orderItemRepository;
    public final CartItemRepository cartItemRepository;

    public Product getProduct(){
        return productRepository.getById(UNIMPORTANT_ID);
    }

    public ProductOffer getProductOffer(){
        return productOfferRepository.getById(UNIMPORTANT_ID);
    }

    public Store getStore(){
        return storeRepository.getById(UNIMPORTANT_ID);
    }

    public Customer getCustomer(){
        return customerRepository.getById(UNIMPORTANT_ID);
    }

    public Order getOrder(){
        return orderRepository.getById(UNIMPORTANT_ID);
    }

    public OrderItem getOrderItem(){
        return orderItemRepository.getById(UNIMPORTANT_ID);
    }

    public CartItem getCartItem(){
        return cartItemRepository.getById(UNIMPORTANT_ID);
    }
}
