package ru.teamtwo.website.controller.user;

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
import ru.teamtwo.core.dtos.user.OrderItemDto;
import ru.teamtwo.core.models.ProductOffer;
import ru.teamtwo.core.models.user.Order;
import ru.teamtwo.core.models.user.OrderItem;
import ru.teamtwo.website.service.user.OrderItemService;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/order_item")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("{id}")
    public OrderItemDto get(@PathVariable Integer id){
        return orderItemService.getItem(id);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody OrderItemDto dto){
        try {
            Integer orderItemId = orderItemService.addItem(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItemId);
        }
        catch(Exception e){
            log.debug("error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
