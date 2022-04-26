package ru.teamtwo.website.controller.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.website.exception.ItemNotFoundException;
import ru.teamtwo.website.service.customer.OrderService;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public OrderDto get(@PathVariable Integer id) {
        try {
            return orderService.getId(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Order " + id + " does not exist");
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
            log.debug("error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

