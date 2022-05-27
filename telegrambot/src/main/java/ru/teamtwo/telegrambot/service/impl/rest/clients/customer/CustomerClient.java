package ru.teamtwo.telegrambot.service.impl.rest.clients.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.teamtwo.core.dtos.controller.customer.CustomerController;
import ru.teamtwo.core.dtos.customer.CustomerDto;

import java.util.Set;

@FeignClient(url = "${telegrambot.rest.webClientUri}customer", name="customerId")
public interface CustomerClient extends CustomerController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<CustomerDto> get(@PathVariable Long id);

    @Override
    @PostMapping("")
    ResponseEntity<Set<Long>> save(@RequestBody Set<CustomerDto> dto);
}
