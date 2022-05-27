package ru.teamtwo.api.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.service.api.customer.OrderItemService;
import ru.teamtwo.core.dtos.controller.customer.OrderItemController;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("order_item")
public class OrderItemControllerImpl implements OrderItemController {
    private final OrderItemService orderItemService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<OrderItemDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.get(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Set<Long>> save(@RequestBody Set<OrderItemDto> dto){
        return ResponseEntity.ok(orderItemService.save(dto));
    }

    @Override
    @GetMapping("byOrder/{id}")
    public ResponseEntity<Set<OrderItemDto>> getAllByOrder(Long orderId) {
        return ResponseEntity.ok(orderItemService.getAllByOrder(orderId));
    }
}
