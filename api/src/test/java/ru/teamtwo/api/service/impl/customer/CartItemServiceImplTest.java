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
import static ru.teamtwo.api.TestUtils.SPECIFIC_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;

@ExtendWith(SpringExtension.class)
class CartItemServiceImplTest {
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
        cartItem = new CartItem(SPECIFIC_ID, null, null, UNIMPORTANT_NUMBER);
        cartItemDto = new CartItemDto(UNIMPORTANT_ID, UNIMPORTANT_ID, UNIMPORTANT_ID, UNIMPORTANT_NUMBER);
    }

    @Test
    void get() {
        assertThatThrownBy(()-> {
            cartItemService.get(cartItem.getId());
            verify(cartItemRepository).existsById(cartItem.getId());
        }).isInstanceOf(ItemNotFoundException.class);

        reset(cartItemRepository, cartItemMapper);

        when(cartItemRepository.existsById(cartItem.getId())).thenReturn(true);
        when(cartItemRepository.getById(cartItem.getId())).thenReturn(cartItem);
        when(cartItemMapper.convert(cartItem)).thenReturn(cartItemDto);
        assertThat(cartItemService.get(cartItem.getId())).isEqualTo(cartItemDto);
        verify(cartItemRepository).existsById(cartItem.getId());
        verify(cartItemRepository).getById(cartItem.getId());
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
        assertThat(cartItemService.save(Collections.singleton(cartItemDto))).isEqualTo(Collections.singleton(cartItem.getId()));
        verify(cartItemMapper).convert(cartItemDto);
        verify(cartItemRepository).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(cartItem)));
    }

    @Test
    void getAllByCustomer() {
        when(cartItemRepository.getCartItemsByCustomer_Id(UNIMPORTANT_ID)).thenReturn(Collections.singleton(cartItem));
        when(cartItemMapper.convert(cartItem)).thenReturn(cartItemDto);
        assertThat(cartItemService.getAllByCustomer(UNIMPORTANT_ID)).contains(cartItemDto);
        verify(cartItemRepository).getCartItemsByCustomer_Id(UNIMPORTANT_ID);
        verify(cartItemMapper).convert(cartItem);
    }
}