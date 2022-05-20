package ru.teamtwo.api.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.service.api.customer.CartItemService;
import ru.teamtwo.core.dtos.controller.customer.CartItemController;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace/api/cart_item")
public class CartItemControllerImpl implements CartItemController {
    private final CartItemService cartItemService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<CartItemDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getItem(id));
    }

    @Override
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> save(CartItemDto dto) {
        try {
            //repository.save(new CartItem(dto));
        } catch (Exception e) {
            throw new UnableToAddItemException("Unable to save item " + dto.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseBody
    @GetMapping("byCustomer/{customerId}")
    public ResponseEntity<Set<CartItemDto>> getAllByCustomer(@PathVariable Long customerId) {
        try {
            CartItemArrayDto body = cartItemService.getState(customerId);
            return ResponseEntity.ok(body);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Customers " + customerId + " cart not found");
        }
    }

    @Override
    @ResponseBody
    @PostMapping("byCustomer/{customerId}")
    public ResponseEntity<Set<Integer>> saveAllByCustomer(@PathVariable Long customerId, @RequestBody Set<CartItemDto> cartItems) {
        try {
            cartItemService.saveState(customerId, cartItems);
        }
        catch (Exception e) {
            throw new UnableToAddItemException("Unable to save cart state for customerId " + customerId);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
