package ru.teamtwo.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.user.CustomerDto;

@FeignClient(url = "localhost:8081/marketplace/api/customer", name="customer")
public interface CustomerController {
    @GetMapping("{id}")
    public CustomerDto get(@PathVariable Integer id);;

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody CustomerDto dto);
}
