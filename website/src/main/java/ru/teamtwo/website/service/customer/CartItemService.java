package ru.teamtwo.website.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.models.customer.CartItem;
import ru.teamtwo.website.repository.ProductRepository;
import ru.teamtwo.website.repository.customer.CartItemRepository;
import ru.teamtwo.website.repository.customer.CustomerRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartItemService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartItemRepository repository;

    public CartItemDto getItem(Integer id){
        log.debug("get: {}", id);
        return new CartItemDto(repository.getById(id));
    }

    public void saveState(Integer customerId, CartItemArrayDto cartItems) {
        log.debug("saveCartState: {}, {}", customerId, cartItems.toString());
        Set<CartItem> cartItems1 = cartItems
                .getCartItemDtoList()
                .stream()
                .map(item -> {
                    log.debug("{}, {}, {}", item.getProductId(), item.getCustomer(), item.getQuantity());
                    CartItem cartItem = new CartItem();
                    cartItem.setQuantity(item.getQuantity());
                    cartItem.setProduct(productRepository.getById(item.getProductId()));
                    cartItem.setCustomer(customerRepository.getById(customerId));
                    return cartItem;
                })
                .collect(Collectors.toSet());
        repository.saveAll(cartItems1);
    }

    public CartItemArrayDto getState(Integer customerId) {
        log.debug("getCartState: {}", customerId);
        Set<CartItem> cartItems = repository.getCartItemsByCustomer_Id(customerId);
        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto();
        cartItemArrayDto.setCartItemDtoList(cartItems
                .stream()
                .map(CartItemDto::new)
                .collect(Collectors.toSet()));
        return cartItemArrayDto;
    }
}
