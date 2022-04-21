package ru.teamtwo.website.service.user;

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

    public Integer addOrder(OrderDto dto){
        log.debug("post: {}", dto.toString());
        Order order = new Order();
        Customer customer = customerRepository.getById(dto.getCustomerId());
        order.setCustomer(customer);
        //order.setCreatedOn(dto.getCreatedOn());
        order = repository.save(order);
        Integer orderId = order.getId();
        return orderId;
    }
}
