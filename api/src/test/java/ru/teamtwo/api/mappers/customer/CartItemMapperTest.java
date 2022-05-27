package ru.teamtwo.api.mappers.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.repository.customer.CartItemRepository;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.customer.CartItemDto;

import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;
import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class CartItemMapperTest {
    @Autowired
    CartItemMapper cartItemMapper;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductOfferRepository productOfferRepository;
    CartItem cartItem;
    CartItemDto cartItemDto;
    @BeforeEach
    void setUp() {
        cartItem = cartItemRepository.save(new CartItem(null,
                customerRepository.getById(UNIMPORTANT_ID),
                productOfferRepository.getById(UNIMPORTANT_ID),
                UNIMPORTANT_NUMBER));
        cartItemDto = new CartItemDto(cartItem.getId(),
                cartItem.getCustomer().getId(),
                cartItem.getProductOffer().getId(),
                cartItem.getQuantity());
    }

    @Test
    void convertToDto() {
        CartItemDto convert = cartItemMapper.convert(cartItem);
        assertDtoAndEntityEqual(convert, cartItem);
    }

    @Test
    void convertFromDto() {
        CartItem convert = cartItemMapper.convert(cartItemDto);
        assertDtoAndEntityEqual(cartItemDto, convert);
    }
}