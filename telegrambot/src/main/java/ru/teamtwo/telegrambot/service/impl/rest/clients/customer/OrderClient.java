package ru.teamtwo.telegrambot.service.impl.rest.clients.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.teamtwo.core.dtos.controller.customer.OrderController;
import ru.teamtwo.core.dtos.customer.OrderDto;

import java.util.Set;

@FeignClient(url = "${telegrambot.rest.webClientUri}order", name="order")
public interface OrderClient extends OrderController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<OrderDto> get(@PathVariable Long id);

    @Override
    @PostMapping("")
    ResponseEntity<Set<Long>> save(@RequestBody Set<OrderDto> dto);

    @Override
    @GetMapping("byCustomer/{customerId}")
    ResponseEntity<Set<OrderDto>> getAllByCustomer(@PathVariable Long customerId);
}
