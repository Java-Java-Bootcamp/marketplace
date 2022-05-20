package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.models.customer.OrderItem;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.repository.customer.OrderItemRepository;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.api.service.api.customer.OrderItemService;
import ru.teamtwo.core.dtos.customer.OrderItemDto;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository repository;
    private final ProductOfferRepository productOfferRepository;

    @Override
    public OrderItemDto get(Long id) {
        return new OrderItemDto(repository.getById(id));
    }

    @Override
    public Long save(OrderItemDto dto) {
        OrderItem orderItem = new OrderItem();
        ProductOffer productOffer = productOfferRepository.getProductOfferById(dto.getProductOfferId());
        Order order = orderRepository.getById(dto.getOrderId());
        orderItem.setProductOffer(productOffer);
        orderItem.setOrder(order);
        orderItem.setQuantity(dto.getQuantity());
        orderItem = repository.save(orderItem);
        Integer orderItemId = orderItem.getId();
        return orderItemId;
    }

    @Override
    public Set<OrderItemDto> getAllByOrder(Integer orderId) {
        return null;
    }

    @Override
    public Set<Integer> saveAllByOrder(Integer orderId, Set<OrderItemDto> objects) {
        return null;
    }
}
