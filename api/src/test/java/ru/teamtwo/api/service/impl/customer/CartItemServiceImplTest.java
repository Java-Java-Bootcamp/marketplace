package ru.teamtwo.api.service.impl.customer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.jdbc.datasource.init.ScriptParseException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.ServerRuntimeException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.mappers.customer.CartItemMapper;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.repository.customer.CartItemRepository;
import ru.teamtwo.core.dtos.customer.CartItemDto;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CartItemServiceImplTest {
    final long ITEM_ID = 100L;
    final long CUSTOMER_ID = 123L;

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartItemMapper cartItemMapper;
    private CartItemDto cartItemDto;
    private CartItem cartItem;
    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    void setUp() {
        cartItem = new CartItem(ITEM_ID, null, null, 1);
        cartItemDto = new CartItemDto(ITEM_ID, 1L, 1L, 1);
    }

    @Test
    void get() {
        assertThatThrownBy(()-> {
            cartItemService.get(ITEM_ID);
            verify(cartItemRepository).existsById(ITEM_ID);
        }).isInstanceOf(ItemNotFoundException.class);

        reset(cartItemRepository, cartItemMapper);

        when(cartItemRepository.existsById(ITEM_ID)).thenReturn(true);
        when(cartItemRepository.getById(ITEM_ID)).thenReturn(cartItem);
        when(cartItemMapper.convert(cartItem)).thenReturn(cartItemDto);
        assertThat(cartItemService.get(ITEM_ID)).isEqualTo(cartItemDto);
        verify(cartItemRepository).existsById(ITEM_ID);
        verify(cartItemRepository).getById(ITEM_ID);
        verify(cartItemMapper).convert(cartItem);
    }

    @Test
    void save() {
        when(cartItemMapper.convert(cartItemDto)).thenReturn(cartItem);
        when(cartItemRepository.saveAll(any())).thenThrow(RuntimeException.class);
        assertThatThrownBy(()-> {
            cartItemService.save(Collections.singleton(cartItemDto));
            verify(cartItemMapper).convert(cartItemDto);
            verify(cartItemRepository).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(cartItem)));
        }).isInstanceOf(ServerRuntimeException.class);

        reset(cartItemRepository, cartItemMapper);

        when(cartItemMapper.convert(cartItemDto)).thenReturn(cartItem);
        when(cartItemRepository.saveAll(any())).thenThrow(ScriptParseException.class);
        assertThatThrownBy(()-> {
            cartItemService.save(Collections.singleton(cartItemDto));
            verify(cartItemMapper).convert(cartItemDto);
            verify(cartItemRepository).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(cartItem)));
        }).isInstanceOf(UnableToAddItemException.class);

        reset(cartItemRepository, cartItemMapper);

        when(cartItemMapper.convert(cartItemDto)).thenReturn(cartItem);
        when(cartItemRepository.saveAll(MockitoHamcrest.argThat(Matchers.hasItems(cartItem)))).thenReturn(Collections.singletonList(cartItem));
        assertThat(cartItemService.save(Collections.singleton(cartItemDto))).isEqualTo(Collections.singleton(ITEM_ID));
        verify(cartItemMapper).convert(cartItemDto);
        verify(cartItemRepository).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(cartItem)));
    }

    @Test
    void getAllByCustomer() {
        when(cartItemRepository.getCartItemsByCustomer_Id(CUSTOMER_ID)).thenReturn(Collections.singleton(cartItem));
        when(cartItemMapper.convert(cartItem)).thenReturn(cartItemDto);
        assertThat(cartItemService.getAllByCustomer(CUSTOMER_ID)).contains(cartItemDto);
        verify(cartItemRepository).getCartItemsByCustomer_Id(CUSTOMER_ID);
        verify(cartItemMapper).convert(cartItem);
    }
}