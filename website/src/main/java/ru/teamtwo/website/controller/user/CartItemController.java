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
import ru.teamtwo.website.service.user.CartItemService;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("{id}")
    public CartItemDto get(@PathVariable Integer id) {
        return cartItemService.getItem(id);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(CartItemDto dto) {
        log.debug("post: {}", dto.toString());
        try {
            //repository.save(new CartItem(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseBody
    @PostMapping("save_cart_state/{customerId}")
    public ResponseEntity<?> saveCartState(@PathVariable Integer customerId, @RequestBody CartItemArrayDto cartItems) {
        try {
            cartItemService.saveState(customerId, cartItems);
        }
        catch (Exception e) {
            log.error("saveCartState error: {}", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseBody
    @GetMapping("get_cart_state/{customerId}")
    public ResponseEntity<CartItemArrayDto> getCartState(@PathVariable Integer customerId) {
        try {
            CartItemArrayDto body = cartItemService.getState(customerId);
            return ResponseEntity.status(HttpStatus.OK).body(body);
        }
        catch (Exception e) {
            log.error("getCartState error: {}", e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
