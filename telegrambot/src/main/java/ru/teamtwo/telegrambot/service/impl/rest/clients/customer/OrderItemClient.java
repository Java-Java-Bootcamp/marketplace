package ru.teamtwo.telegrambot.service.impl.rest.clients.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.teamtwo.core.dtos.controller.customer.OrderItemController;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

import java.util.Set;

@FeignClient(url = "${telegrambot.rest.webClientUri}order_item", name="orderItem")
public interface OrderItemClient extends OrderItemController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<OrderItemDto> get(@PathVariable Long id);

    @Override
    @PostMapping("")
    ResponseEntity<Set<Long>> save(@RequestBody Set<OrderItemDto> dto);

    @Override
    @GetMapping("byCustomer/{customerId}")
    ResponseEntity<Set<OrderItemDto>> getAllByOrder(@PathVariable Long customerId);
}
