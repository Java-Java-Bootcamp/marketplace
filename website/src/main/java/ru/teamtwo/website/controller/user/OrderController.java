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
import ru.teamtwo.core.dtos.user.OrderDto;
import ru.teamtwo.core.models.user.Customer;
import ru.teamtwo.core.models.user.Order;
import ru.teamtwo.website.repository.user.CustomerRepository;
import ru.teamtwo.website.repository.user.OrderRepository;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/order")
public class OrderController {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    @GetMapping("{id}")
    public OrderDto get(@PathVariable Integer id){
        log.debug("get: {}", id);
        return new OrderDto(repository.getById(id));
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> post(@RequestBody OrderDto dto){
        log.debug("post: {}", dto.toString());
        try {
            Order order = new Order();
            Customer customer = customerRepository.getById(dto.getCustomerId());
            order.setCustomer(customer);
            //order.setCreatedOn(dto.getCreatedOn());
            order = repository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(order.getId());
        }catch(Exception e){
            log.debug("error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
