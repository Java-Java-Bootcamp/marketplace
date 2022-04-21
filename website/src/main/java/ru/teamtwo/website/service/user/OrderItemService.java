package ru.teamtwo.website.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.teamtwo.core.dtos.user.OrderItemDto;
import ru.teamtwo.core.models.ProductOffer;
import ru.teamtwo.core.models.user.Order;
import ru.teamtwo.core.models.user.OrderItem;
import ru.teamtwo.website.repository.ProductOfferRepository;
import ru.teamtwo.website.repository.user.OrderItemRepository;
import ru.teamtwo.website.repository.user.OrderRepository;

@Slf4j
public class OrderItemService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository repository;
    @Autowired
    private ProductOfferRepository productOfferRepository;

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
