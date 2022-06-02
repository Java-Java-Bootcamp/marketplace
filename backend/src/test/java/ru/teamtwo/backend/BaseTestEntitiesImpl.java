package ru.teamtwo.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.backend.mappers.customer.CartItemMapper;
import ru.teamtwo.backend.mappers.customer.CustomerMapper;
import ru.teamtwo.backend.mappers.customer.OrderItemMapper;
import ru.teamtwo.backend.mappers.customer.OrderMapper;
import ru.teamtwo.backend.mappers.product.ProductMapper;
import ru.teamtwo.backend.mappers.product.ProductOfferMapper;
import ru.teamtwo.backend.mappers.product.StoreMapper;
import ru.teamtwo.backend.models.customer.CartItem;
import ru.teamtwo.backend.models.customer.Customer;
import ru.teamtwo.backend.models.customer.Order;
import ru.teamtwo.backend.models.customer.OrderItem;
import ru.teamtwo.backend.models.product.Product;
import ru.teamtwo.backend.models.product.ProductOffer;
import ru.teamtwo.backend.models.product.Store;
import ru.teamtwo.backend.repository.customer.CartItemRepository;
import ru.teamtwo.backend.repository.customer.CustomerRepository;
import ru.teamtwo.backend.repository.customer.OrderItemRepository;
import ru.teamtwo.backend.repository.customer.OrderRepository;
import ru.teamtwo.backend.repository.product.ProductOfferRepository;
import ru.teamtwo.backend.repository.product.ProductRepository;
import ru.teamtwo.backend.repository.product.StoreRepository;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.core.dtos.product.ProductDto;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.core.dtos.product.StoreDto;

import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_ID;

@Component
@Transactional
@RequiredArgsConstructor
public class BaseTestEntitiesImpl implements BaseTestEntities {
    public final ProductMapper productMapper;
    public final ProductOfferMapper productOfferMapper;
    public final StoreMapper storeMapper;
    public final CustomerMapper customerMapper;
    public final OrderMapper orderMapper;
    public final OrderItemMapper orderItemMapper;
    public final CartItemMapper cartItemMapper;

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

    public ProductDto getProductDto(){
        return productMapper.convertToDto(getProduct());
    }

    public ProductOfferDto getProductOfferDto(){
        return productOfferMapper.convertToDto(getProductOffer());
    }

    public StoreDto getStoreDto(){
        return storeMapper.convertToDto(getStore());
    }

    public CustomerDto getCustomerDto(){
        return customerMapper.convertToDto(getCustomer());
    }

    public OrderDto getOrderDto(){
        return orderMapper.convertToDto(getOrder());
    }

    public OrderItemDto getOrderItemDto(){
        return orderItemMapper.convertToDto(getOrderItem());
    }

    public CartItemDto getCartItemDto(){
        return cartItemMapper.convertToDto(getCartItem());
    }
}
