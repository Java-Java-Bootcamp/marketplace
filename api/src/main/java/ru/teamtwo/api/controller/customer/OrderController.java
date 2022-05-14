package ru.teamtwo.api.controller.customer;

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
import ru.teamtwo.api.service.customer.OrderService;
import ru.teamtwo.core.dtos.customer.OrderDto;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    public OrderDto get(@PathVariable Integer id) {
        try {
            return orderService.getOrderById(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Can't get order " + id);
        }
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> post(@RequestBody OrderDto dto){
        try {
            Integer orderId = orderService.addOrder(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        }
        catch(Exception e){
            throw new UnableToAddItemException("Unable to add order " + dto.toString());
        }
    }
}

