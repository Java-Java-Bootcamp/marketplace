package ru.teamtwo.telegrambot.client.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;

@FeignClient(url = "${telegrambot.rest.webClientUri}/marketplace/api/cart_item", name="cartItem")
public interface CartItemClient {
    @GetMapping("{id}")
    CartItemDto get(@PathVariable Integer id);

    @ResponseBody
    @PostMapping("")
    ResponseEntity<?> post(CartItemDto dto);

    @ResponseBody
    @PostMapping("save_cart_state/{customerId}")
    ResponseEntity<?> saveCartState(@PathVariable Long customerId, @RequestBody CartItemArrayDto cartItems);

    @ResponseBody
    @GetMapping("get_cart_state/{customerId}")
    ResponseEntity<CartItemArrayDto> getCartState(@PathVariable Long customerId);
}
