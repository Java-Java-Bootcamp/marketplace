package ru.teamtwo.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.user.OrderDto;

@FeignClient(url = "localhost:8081/marketplace/api/order", name="order")
public interface OrderController {
    @GetMapping("{id}")
    OrderDto get(@PathVariable Integer id);

    @ResponseBody
    @PostMapping("")
    ResponseEntity<Integer> post(@RequestBody OrderDto dto);
}
