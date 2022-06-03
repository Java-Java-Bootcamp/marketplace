package ru.teamtwo.backend.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.backend.service.api.product.StoreService;
import ru.teamtwo.core.dtos.controller.product.StoreController;
import ru.teamtwo.core.dtos.product.StoreDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("store")
public class StoreControllerImpl implements StoreController {
    private final StoreService storeService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<StoreDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.get(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Set<Long>> save(@RequestBody Set<StoreDto> storeDtos) {
        log.debug("hi");
        return ResponseEntity.ok(storeService.save(storeDtos));
    }
}
