package ru.teamtwo.api.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.service.api.customer.OrderItemService;
import ru.teamtwo.core.dtos.controller.customer.OrderItemController;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace/api/order_item")
public class OrderItemControllerImpl implements OrderItemController {
    private final OrderItemService orderItemService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<OrderItemDto> get(@PathVariable Long id) {
        try {
            return orderItemService.getItem(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Can't get order item " + id);
        }
    }

    @Override
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody OrderItemDto dto){
        try {
            Integer orderItemId = orderItemService.addItem(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItemId);
        }
        catch(Exception e){
            throw new UnableToAddItemException("Unable to add order item " + dto.toString());
        }
    }

    @Override
    @GetMapping("byOrder/{id}")
    public ResponseEntity<Set<OrderItemDto>> getAllByOrder(Integer orderId) {
        return null;
    }

    @Override
    @ResponseBody
    @PostMapping("byOrder/{id}")
    public ResponseEntity<Set<Integer>> saveAllByOrder(Integer orderId, Set<OrderItemDto> objects) {
        return null;
    }
}
