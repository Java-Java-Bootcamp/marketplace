package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.mappers.customer.OrderItemMapper;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.repository.customer.OrderItemRepository;
import ru.teamtwo.api.service.api.customer.OrderItemService;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemDto get(Long id) {
        if(orderItemRepository.existsById(id))
            return orderItemMapper.convert(orderItemRepository.getById(id));
        else
            throw new ItemNotFoundException();
    }

    @Override
    public Set<Long> save(Set<OrderItemDto> dtos) {
        return orderItemRepository
                .saveAll(dtos.stream().map(orderItemMapper::convert).collect(Collectors.toSet()))
                .stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItemDto> getAllByOrder(Long orderId) {
        return orderItemRepository.getOrderItemsByOrder_Id(orderId)
                .stream()
                .map(orderItemMapper::convert)
                .collect(Collectors.toSet());
    }
}
