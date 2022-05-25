package ru.teamtwo.api.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.service.api.product.StoreService;
import ru.teamtwo.core.dtos.controller.product.StoreController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;
import ru.teamtwo.core.dtos.product.StoreDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
@RequestMapping("/marketplace/api/store")
public class StoreControllerImpl implements StoreController {
    private final StoreService storeService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<StoreDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.get(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Set<Long>> save(Set<StoreDto> storeDtos) {
        return ResponseEntity.ok(storeService.save(storeDtos));
    }
}
