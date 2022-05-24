package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.mappers.customer.CartItemMapper;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.repository.customer.CartItemRepository;
import ru.teamtwo.api.service.api.customer.CartItemService;
import ru.teamtwo.core.dtos.customer.CartItemDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto get(Long id) {
        if(cartItemRepository.existsById(id))
            return cartItemMapper.convert(cartItemRepository.getById(id));
        else
            throw new ItemNotFoundException();
    }

    @Override
    public Set<Long> save(Set<CartItemDto> dtos) {
        return cartItemRepository
                .saveAll(dtos.stream().map(cartItemMapper::convert).collect(Collectors.toSet()))
                .stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<CartItemDto> getAllByCustomer(Long customerId) {
        return cartItemRepository.getCartItemsByCustomer_Id(customerId)
                .stream()
                .map(cartItemMapper::convert)
                .collect(Collectors.toSet());
    }
}
