package ru.teamtwo.api.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.repository.ProductOfferRepository;
import ru.teamtwo.api.repository.customer.OrderItemRepository;
import ru.teamtwo.api.repository.customer.OrderRepository;
import ru.teamtwo.core.dtos.customer.OrderItemDto;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.models.customer.Order;
import ru.teamtwo.api.models.customer.OrderItem;

@Slf4j
@Service
public class OrderItemService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository repository;
    private final ProductOfferRepository productOfferRepository;

    public OrderItemService(OrderRepository orderRepository, OrderItemRepository repository, ProductOfferRepository productOfferRepository) {
        this.orderRepository = orderRepository;
        this.repository = repository;
        this.productOfferRepository = productOfferRepository;
    }

    public OrderItemDto getItem(Integer id){
        log.debug("get: {}", id);
        return new OrderItemDto(repository.getById(id));
    }

    public Integer addItem(OrderItemDto dto){
        log.debug("post: {}", dto.toString());
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
}
