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
import ru.teamtwo.api.service.api.customer.OrderService;
import ru.teamtwo.core.dtos.controller.customer.OrderController;
import ru.teamtwo.core.dtos.customer.OrderDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace/api/order")
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<OrderDto> get(@PathVariable Long id) {
        try {
            return orderService.getOrderById(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Can't get order " + id);
        }
    }

    @Override
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody OrderDto dto){
        try {
            Integer orderId = orderService.addOrder(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        }
        catch(Exception e){
            throw new UnableToAddItemException("Unable to add order " + dto.toString());
        }
    }

    @Override
    @GetMapping("byCustomer/{customerId}")
    public ResponseEntity<Set<OrderDto>> getAllByCustomer(Long customerId) {
        return null;
    }

    @Override
    @ResponseBody
    @PostMapping("byCustomer/{customerId}")
    public ResponseEntity<Set<Integer>> saveAllToCustomer(Long customerId, Set<OrderDto> objects) {
        return null;
    }
}

