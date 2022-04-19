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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.website.dtos.user.CartItemArrayDto;
import ru.teamtwo.website.dtos.user.CartItemDto;
import ru.teamtwo.website.dtos.user.CustomerDto;
import ru.teamtwo.website.model.user.CartItem;
import ru.teamtwo.website.model.user.Customer;
import ru.teamtwo.website.repository.ProductOfferRepository;
import ru.teamtwo.website.repository.user.CartItemRepository;
import ru.teamtwo.website.repository.user.CustomerRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/customer")
public class CustomerController {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository repository;

    @GetMapping("{id}")
    public CustomerDto get(@PathVariable Integer id){
        log.debug("get: {}", id);
        return new CustomerDto(repository.getById(id));
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody CustomerDto dto){
        log.debug("post: {}", dto.toString());
        try {
            repository.save(new Customer(dto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
