package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.mappers.customer.CartItemMapper;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.repository.customer.CartItemRepository;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.product.ProductRepository;
import ru.teamtwo.api.service.api.customer.CartItemService;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository repository;

    @Override
    public CartItemDto get(Long id) {
        CartItemMapper mapper = Mappers.getMapper(CartItemMapper.class);
        CartItemDto cartItemDto = mapper.convert(repository.getById(id));
        return cartItemDto;
    }

    @Override
    public Integer save(CartItemDto dto) {
        return null;
    }

    @Override
    public Set<CartItemDto> getAllByCustomer(Long customerId) {
        log.debug("getCartState: {}", customerId);
        Set<CartItem> cartItems = repository.getCartItemsByCustomer_Id(customerId);
        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto();
        cartItemArrayDto.setCartItemDtoList(cartItems
                .stream()
                .map(CartItemDto::new)
                .collect(Collectors.toSet()));
        return cartItemArrayDto;
    }

    @Override
    public Set<Integer> saveAllByCustomer(Long customerId, Set<CartItemDto> objects) {
        log.debug("saveCartState: {}, {}", customerId, cartItems.toString());
        Set<CartItem> cartItems1 = cartItems
                .getCartItemDtoList()
                .stream()
                .map(item -> {
                    log.debug("{}, {}, {}", item.getProductId(), item.getCustomer(), item.getQuantity());
                    CartItem cartItem = new CartItem();
                    cartItem.setQuantity(item.getQuantity());
                    cartItem.setProduct(productRepository.getById(item.getProductId()));
                    cartItem.setCustomer(customerRepository.getById(customerId));
                    return cartItem;
                })
                .collect(Collectors.toSet());
        repository.saveAll(cartItems1);
    }
}
