package ru.teamtwo.api.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.teamtwo.core.dtos.customer.OrderDto;
import ru.teamtwo.core.models.customer.Customer;
import ru.teamtwo.core.models.customer.Order;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.customer.OrderRepository;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    public OrderDto getOrderById(Integer id){
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
