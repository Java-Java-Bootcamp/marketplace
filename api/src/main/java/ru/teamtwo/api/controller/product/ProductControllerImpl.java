package ru.teamtwo.api.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.service.api.product.ProductService;
import ru.teamtwo.core.dtos.controller.product.ProductController;
import ru.teamtwo.core.dtos.product.ProductDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Set<Long>> save(@RequestBody Set<ProductDto> dtos) {
        return ResponseEntity.ok(productService.save(dtos));
    }
}
