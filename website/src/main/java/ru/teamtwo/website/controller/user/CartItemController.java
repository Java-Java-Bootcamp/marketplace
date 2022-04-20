package ru.teamtwo.website.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.core.dtos.user.CartItemArrayDto;
import ru.teamtwo.core.dtos.user.CartItemDto;
import ru.teamtwo.core.models.user.CartItem;
import ru.teamtwo.website.repository.ProductRepository;
import ru.teamtwo.website.repository.user.CartItemRepository;
import ru.teamtwo.website.repository.user.CustomerRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/cart_item")
public class CartItemController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartItemRepository repository;

    @GetMapping("{id}")
    public CartItemDto get(@PathVariable Integer id){
        log.debug("get: {}", id);
        return new CartItemDto(repository.getById(id));
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(CartItemDto dto){
        log.debug("post: {}", dto.toString());
        try {
            //repository.save(new CartItem(dto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseBody
    @PostMapping("save_cart_state/{customerId}")
    public ResponseEntity<?> saveCartState(@PathVariable Integer customerId, @RequestBody CartItemArrayDto cartItems){
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

    @ResponseBody
    @GetMapping("get_cart_state/{customerId}")
    public ResponseEntity<?> getCartState(@PathVariable Integer customerId){
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
