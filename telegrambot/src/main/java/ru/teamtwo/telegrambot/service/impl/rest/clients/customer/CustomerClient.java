package ru.teamtwo.telegrambot.service.impl.rest.clients.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamtwo.core.dtos.controller.customer.CustomerController;
import ru.teamtwo.core.dtos.customer.CustomerDto;

@FeignClient(url = "${telegrambot.rest.webClientUri}/marketplace/api/customer", name="customerId")
public interface CustomerClient extends CustomerController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<CustomerDto> get(@PathVariable Long id);

    @Override
    @ResponseBody
    @PostMapping("")
    ResponseEntity<Integer> save(CustomerDto dto);
}
