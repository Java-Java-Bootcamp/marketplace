package ru.teamtwo.backend.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.backend.mappers.customer.CartItemMapper;
import ru.teamtwo.backend.models.customer.CartItem;
import ru.teamtwo.backend.repository.customer.CartItemRepository;
import ru.teamtwo.backend.service.api.customer.CartItemService;
import ru.teamtwo.backend.service.impl.ServiceUtils;
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
        return cartItemMapper.convertToDto((CartItem) ServiceUtils.get(cartItemRepository, id));
    }

    @Override
    public Set<Long> save(Set<CartItemDto> dtos) {
        return ServiceUtils.save(() -> cartItemRepository
                .saveAll(dtos.stream().map(cartItemMapper::convertToEntity).collect(Collectors.toSet()))
                .stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet()), log);
    }

    @Override
    public Set<CartItemDto> getAllByCustomer(Long customerId) {
        return cartItemRepository.getByCustomer_Id(customerId)
                .stream()
                .map(cartItemMapper::convertToDto)
                .collect(Collectors.toSet());
    }
}
