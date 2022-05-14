package ru.teamtwo.telegrambot.client.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

@FeignClient(url = "${telegrambot.rest.webClientUri}/marketplace/api/order_item", name="orderItem")
public interface OrderItemClient {
    @GetMapping("{id}")
    OrderItemDto get(@PathVariable Integer id);

    @ResponseBody
    @PostMapping("")
    ResponseEntity<?> post(@RequestBody OrderItemDto dto);
}
