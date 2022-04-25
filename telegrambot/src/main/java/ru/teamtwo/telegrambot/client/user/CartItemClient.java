package ru.teamtwo.telegrambot.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.user.CartItemArrayDto;
import ru.teamtwo.core.dtos.user.CartItemDto;

@FeignClient(url = "localhost:8081/marketplace/api/cart_item", name="cartItem")
public interface CartItemClient {
    @GetMapping("{id}")
    CartItemDto get(@PathVariable Integer id);

    @ResponseBody
    @PostMapping("")
    ResponseEntity<?> post(CartItemDto dto);

    @ResponseBody
    @PostMapping("save_cart_state/{customerId}")
    ResponseEntity<?> saveCartState(@PathVariable Integer customerId, @RequestBody CartItemArrayDto cartItems);

    @ResponseBody
    @GetMapping("get_cart_state/{customerId}")
    public ResponseEntity<CartItemArrayDto> getCartState(@PathVariable Integer customerId);
}
