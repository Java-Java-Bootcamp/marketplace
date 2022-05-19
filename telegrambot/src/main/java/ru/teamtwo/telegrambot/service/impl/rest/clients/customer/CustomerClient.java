package ru.teamtwo.telegrambot.service.impl.rest.clients.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.customer.CustomerDto;

@FeignClient(url = "${telegrambot.rest.webClientUri}/marketplace/api/customer", name="customerId")
public interface CustomerClient {
    @GetMapping("{id}")
    CustomerDto get(@PathVariable Long id);

    @ResponseBody
    @PostMapping("")
    ResponseEntity<?> post(@RequestBody CustomerDto dto);
}
