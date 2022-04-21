package ru.teamtwo.website.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.core.dtos.user.OrderDto;
import ru.teamtwo.website.service.OrderService;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public OrderDto get(@PathVariable Integer id){
        return orderService.getId(id);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> post(@RequestBody OrderDto dto){
        return orderService.addOrder(dto);
        }
    }

