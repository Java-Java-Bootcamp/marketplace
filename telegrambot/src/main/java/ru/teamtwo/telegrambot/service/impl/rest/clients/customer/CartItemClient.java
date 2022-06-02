package ru.teamtwo.telegrambot.service.impl.rest.clients.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.teamtwo.core.dtos.controller.customer.CartItemController;
import ru.teamtwo.core.dtos.customer.CartItemDto;

import java.util.Set;

@FeignClient(url = "${telegrambot.rest.webClientUri}cart_item", name="cartItem")
public interface CartItemClient extends CartItemController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<CartItemDto> get(@PathVariable Long id);

    @Override
    @PostMapping("")
    ResponseEntity<Set<Long>> save(@RequestBody Set<CartItemDto> dto);

    @Override
    @GetMapping("byCustomer/{customerId}")
    ResponseEntity<Set<CartItemDto>> getAllByCustomer(@PathVariable Long customerId);
}
