package ru.teamtwo.api.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.repository.product.ProductOfferRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.api.TestUtils.ANOTHER_NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.EMPTY_ID;
import static ru.teamtwo.api.TestUtils.NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class CartItemRepositoryTest {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductOfferRepository productOfferRepository;
    CartItem cartItem;
    CartItem setupCartItem;
    Customer customer;
    ProductOffer productOffer;
    @BeforeEach
    void setUp() {
        customer = customerRepository.getById(UNIMPORTANT_ID);
        productOffer = productOfferRepository.getById(UNIMPORTANT_ID);
        setupCartItem = new CartItem(null,customer,productOffer,UNIMPORTANT_NUMBER);
        setupCartItem = cartItemRepository.save(setupCartItem);
    }

    @Test
    void get(){
        assertThat(cartItemRepository.existsById(null)).isFalse();
        assertThat(cartItemRepository.existsById(EMPTY_ID)).isFalse();
        assertThat(cartItemRepository.existsById(setupCartItem.getId())).isTrue();
        assertThat(cartItemRepository.getById(setupCartItem.getId())).isEqualTo(setupCartItem);
    }

    @Test
    void save() {
        //null fields
        cartItem = new CartItem(null, null, null, null);
        assertThatThrownBy(()->cartItemRepository.save(cartItem)).isInstanceOf(DataAccessException.class);

        //basic save
        cartItem = new CartItem(null, customer, productOffer, UNIMPORTANT_NUMBER);
        Long id = cartItemRepository.save(cartItem).getId();

        //overwrite
        cartItem = new CartItem(id, customer, productOffer, NEW_NUMBER);
        CartItem newCartItem = cartItemRepository.save(cartItem);
        assertThat(newCartItem.getId()).isEqualTo(id);
        assertThat(newCartItem.getCustomer().getId()).isEqualTo(UNIMPORTANT_ID);
        assertThat(newCartItem.getQuantity()).isEqualTo(NEW_NUMBER);

        //edit and save
        newCartItem.setQuantity(ANOTHER_NEW_NUMBER);
        newCartItem = cartItemRepository.save(newCartItem);
        assertThat(newCartItem.getQuantity()).isEqualTo(ANOTHER_NEW_NUMBER);
    }

    @Test
    void getCartItemsByCustomer_Id() {
        assertThat(cartItemRepository.getCartItemsByCustomer_Id(null)).isEmpty();
        assertThat(cartItemRepository.getCartItemsByCustomer_Id(EMPTY_ID)).isEmpty();
        assertThat(cartItemRepository.getCartItemsByCustomer_Id(setupCartItem.getCustomer().getId())).contains(setupCartItem);
    }
}