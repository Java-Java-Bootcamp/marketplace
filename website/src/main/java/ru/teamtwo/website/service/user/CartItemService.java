package ru.teamtwo.website.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.teamtwo.core.dtos.user.CartItemArrayDto;
import ru.teamtwo.core.dtos.user.CartItemDto;
import ru.teamtwo.core.models.user.CartItem;
import ru.teamtwo.website.repository.ProductRepository;
import ru.teamtwo.website.repository.user.CartItemRepository;
import ru.teamtwo.website.repository.user.CustomerRepository;

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

    public ResponseEntity<?> addItem(CartItemDto dto){
        log.debug("post: {}", dto.toString());
        try {
            //repository.save(new CartItem(dto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> saveState(Integer customerId, CartItemArrayDto cartItems) {
        log.debug("saveCartState: {}, {}", customerId, cartItems.toString());
        try {
            Set<CartItem> cartItems1 = cartItems
                    .getCartItemDtoList()
                    .stream()
                    .map(item->{
                        log.debug("{}, {}, {}", item.getProductId(), item.getCustomer(), item.getQuantity());
                        CartItem cartItem = new CartItem();
                        cartItem.setQuantity(item.getQuantity());
                        cartItem.setProduct(productRepository.getById(item.getProductId()));
                        cartItem.setCustomer(customerRepository.getById(customerId));
                        return cartItem;
                    })
                    .collect(Collectors.toSet());
            repository.saveAll(cartItems1);
        }catch(Exception e){
            log.error("saveCartState error: {}", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<CartItemArrayDto> getState(Integer customerId) {
        log.debug("getCartState: {}", customerId);
        try {
            Set<CartItem> cartItems = repository.getCartItemsByCustomer_Id(customerId);

            CartItemArrayDto cartItemArrayDto = new CartItemArrayDto();
            cartItemArrayDto.setCartItemDtoList(cartItems
                    .stream()
                    .map(CartItemDto::new)
                    .collect(Collectors.toSet()));
            return ResponseEntity.status(HttpStatus.OK).body(cartItemArrayDto);
        }catch(Exception e){
            log.error("getCartState error: {}", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    }
}
