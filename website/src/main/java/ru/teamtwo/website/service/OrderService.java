package ru.teamtwo.website.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.teamtwo.core.dtos.user.OrderDto;
import ru.teamtwo.core.models.user.Customer;
import ru.teamtwo.core.models.user.Order;
import ru.teamtwo.website.repository.user.CustomerRepository;
import ru.teamtwo.website.repository.user.OrderRepository;

@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    public OrderDto getId(Integer id){
        log.debug("get: {}", id);
        return new OrderDto(repository.getById(id));
    }

    public ResponseEntity<Integer> addOrder(OrderDto dto){
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
