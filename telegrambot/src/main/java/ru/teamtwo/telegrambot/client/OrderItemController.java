package ru.teamtwo.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.user.OrderItemDto;

@FeignClient(url = "localhost:8081/marketplace/api/order_item", name="orderItem")
public interface OrderItemController {
    @GetMapping("{id}")
    public OrderItemDto get(@PathVariable Integer id);

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody OrderItemDto dto);
}
