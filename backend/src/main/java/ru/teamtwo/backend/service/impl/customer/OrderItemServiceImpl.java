package ru.teamtwo.backend.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.backend.mappers.customer.OrderItemMapper;
import ru.teamtwo.backend.models.customer.OrderItem;
import ru.teamtwo.backend.repository.customer.OrderItemRepository;
import ru.teamtwo.backend.service.api.customer.OrderItemService;
import ru.teamtwo.backend.service.impl.ServiceUtils;
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
        return orderItemMapper.convertToDto((OrderItem) ServiceUtils.get(orderItemRepository, id));
    }

    @Override
    public Set<Long> save(Set<OrderItemDto> dtos) {
        return ServiceUtils.save(() -> orderItemRepository
                .saveAll(dtos.stream().map(orderItemMapper::convertToEntity).collect(Collectors.toSet()))
                .stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet()), log);
    }

    @Override
    public Set<OrderItemDto> getAllByOrder(Long orderId) {
        return orderItemRepository.getByOrder_Id(orderId)
                .stream()
                .map(orderItemMapper::convertToDto)
                .collect(Collectors.toSet());
    }
}
