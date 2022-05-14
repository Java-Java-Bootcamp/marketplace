package ru.teamtwo.api.controller.customer;

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
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.service.customer.CartItemService;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("{id}")
    public CartItemDto get(@PathVariable Integer id) {
        try {
            return cartItemService.getItem(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Can't get cart item " + id);
        }
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(CartItemDto dto) {
        try {
            //repository.save(new CartItem(dto));
        } catch (Exception e) {
            throw new UnableToAddItemException("Unable to save item " + dto.toString());
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
            throw new UnableToAddItemException("Unable to save cart state for customer " + customerId);
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
            throw new ItemNotFoundException("Customers " + customerId + " cart not found");
        }
    }
}