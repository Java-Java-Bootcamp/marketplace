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
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.website.exception.ItemNotFoundException;
import ru.teamtwo.website.exception.UnableToAddItemException;
import ru.teamtwo.website.service.customer.OrderItemService;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/order_item")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("{id}")
    public OrderItemDto get(@PathVariable Integer id) {
        try {
            return orderItemService.getItem(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Can't get order item " + id);
        }
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody OrderItemDto dto){
        try {
            Integer orderItemId = orderItemService.addItem(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItemId);
        }
        catch(Exception e){
            throw new UnableToAddItemException("Unable to add order item " + dto.toString());
        }
    }
}
