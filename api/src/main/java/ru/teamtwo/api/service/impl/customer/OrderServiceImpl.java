package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.api.service.api.customer.OrderService;
import ru.teamtwo.core.dtos.customer.OrderDto;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final CustomerRepository customerRepository;

    @Override
    public OrderDto get(Long id) {
        return new OrderDto(repository.getById(id));
    }

    @Override
    public Long save(OrderDto dto) {
        Order order = new Order();
        Customer customer = customerRepository.getById(dto.getCustomerId());
        order.setCustomer(customer);
        //order.setCreatedOn(dto.getCreatedOn());
        order = repository.save(order);
        Integer orderId = order.getId();
        return orderId;
    }

    @Override
    public Set<OrderDto> getAllByCustomer(Long customerId) {
        return null;
    }

    @Override
    public Set<Integer> saveAllByCustomer(Long customerId, Set<OrderDto> objects) {
        return null;
    }
}
