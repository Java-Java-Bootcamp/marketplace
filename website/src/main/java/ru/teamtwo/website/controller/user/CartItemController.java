package ru.teamtwo.website.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.website.dtos.user.CartItemDto;
import ru.teamtwo.website.dtos.user.CustomerDto;
import ru.teamtwo.website.model.user.CartItem;
import ru.teamtwo.website.model.user.Customer;
import ru.teamtwo.website.repository.ProductOfferRepository;
import ru.teamtwo.website.repository.user.CartItemRepository;
import ru.teamtwo.website.repository.user.CustomerRepository;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemRepository repository;

    @GetMapping("{id}")
    public CartItemDto get(@PathVariable long id){
        log.debug("get: {}", id);
        return new CartItemDto(repository.getById(id));
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(CartItemDto dto){
        log.debug("post: {}", dto.toString());
        try {
            repository.save(new CartItem(dto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
