package ru.teamtwo.website.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/marketplace/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("{id}")
    public CartItemDto get(@PathVariable Integer id){
        return cartItemService.getItem(id);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(CartItemDto dto){
        return cartItemService.addItem(dto);
    }

    @ResponseBody
    @PostMapping("save_cart_state/{customerId}")
    public ResponseEntity<?> saveCartState(@PathVariable Integer customerId, @RequestBody CartItemArrayDto cartItems){
        return cartItemService.saveState(customerId, cartItems);
    }

    @ResponseBody
    @GetMapping("get_cart_state/{customerId}")
    public ResponseEntity<CartItemArrayDto> getCartState(@PathVariable Integer customerId){
            return cartItemService.getState(customerId);
        }
    }
