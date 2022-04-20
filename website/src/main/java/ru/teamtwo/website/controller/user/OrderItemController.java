package ru.teamtwo.website.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.website.dtos.user.OrderItemDto;
import ru.teamtwo.website.model.ProductOffer;
import ru.teamtwo.website.model.user.Order;
import ru.teamtwo.website.model.user.OrderItem;
import ru.teamtwo.website.repository.ProductOfferRepository;
import ru.teamtwo.website.repository.user.OrderItemRepository;
import ru.teamtwo.website.repository.user.OrderRepository;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/order_item")
public class OrderItemController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository repository;
    @Autowired
    private ProductOfferRepository productOfferRepository;

    @GetMapping("{id}")
    public OrderItemDto get(@PathVariable Integer id){
        log.debug("get: {}", id);
        return new OrderItemDto(repository.getById(id));
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody OrderItemDto dto){
        log.debug("post: {}", dto.toString());
        try {
            OrderItem orderItem = new OrderItem();
            ProductOffer productOffer = productOfferRepository.getProductOfferById(dto.getProductOfferId());
            Order order = orderRepository.getById(dto.getOrderId());
            orderItem.setProductOffer(productOffer);
            orderItem.setOrder(order);
            orderItem.setQuantity(dto.getQuantity());
            orderItem = repository.save(orderItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItem.getId());
        }catch(Exception e){
            log.debug("error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
