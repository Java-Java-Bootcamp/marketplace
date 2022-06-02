package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.mappers.customer.OrderMapper;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.api.service.api.customer.OrderService;
import ru.teamtwo.api.service.impl.ServiceUtils;
import ru.teamtwo.core.dtos.customer.OrderDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto get(Long id) {
        return orderMapper.convertToDto((Order) ServiceUtils.get(orderRepository, id));
    }

    @Override
    public Set<Long> save(Set<OrderDto> dtos) {
        return ServiceUtils.save(() -> orderRepository
                .saveAll(dtos.stream().map(orderMapper::convertToEntity).collect(Collectors.toSet()))
                .stream()
                .map(Order::getId)
                .collect(Collectors.toSet()), log);
    }

    @Override
    public Set<OrderDto> getAllByCustomer(Long customerId) {
        return orderRepository.getByCustomer_Id(customerId).stream().map(orderMapper::convertToDto).collect(Collectors.toSet());
    }
}
