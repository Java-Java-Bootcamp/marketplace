package ru.teamtwo.telegrambot.service.impl.rest.clients.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.teamtwo.core.dtos.controller.product.StoreController;
import ru.teamtwo.core.dtos.product.StoreDto;

import java.util.Set;

@FeignClient(url = "${telegrambot.rest.webClientUri}store", name="store")
public interface StoreClient extends StoreController {
    @Override
    @GetMapping("{id}")
    ResponseEntity<StoreDto> get(@PathVariable Long id);

    @Override
    @PostMapping("")
    ResponseEntity<Set<Long>> save(@RequestBody Set<StoreDto> dto);
}
