package ru.teamtwo.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.api.repository.ProductOfferRepository;
import ru.teamtwo.api.repository.ProductRepository;
import ru.teamtwo.api.repository.StoreRepository;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.service.customer.CartItemService;
import ru.teamtwo.core.dtos.customer.CartItemArrayDto;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.models.Product;
import ru.teamtwo.core.models.ProductOffer;
import ru.teamtwo.core.models.Store;
import ru.teamtwo.core.models.customer.Customer;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartItemServiceTests {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ProductOfferRepository productOfferRepository;

    @Test
    @Transactional
    void testSaveState(){
        CartItemArrayDto cartItemArrayDto = new CartItemArrayDto();
        Set<CartItemDto> cartItemDtos = new HashSet<>();
        CartItemDto cartItemDto0 = new CartItemDto();
        cartItemDtos.add(cartItemDto0);
        cartItemArrayDto.setCartItemDtoList(cartItemDtos);

        Customer customer = customerRepository.getById(1);

        Store store = new Store();
        store.setName("name");
        store.setRating(100);
        store = storeRepository.save(store);

        Product product = new Product();
        product.setCategory("category");
        product.setDescription("description");
        product.setPrice(100);
        product.setModel("model");
        product.setName("name");
        product.setRating(100);
        product.setManufacturer("manufacturer");
        product = productRepository.save(product);

        ProductOffer productOffer = new ProductOffer();
        productOffer.setQuantity(100);
        productOffer.setStore(store);
        productOffer.setProduct(product);
        productOffer = productOfferRepository.save(productOffer);

        cartItemDto0.setCustomer(customer.getId());
        cartItemDto0.setProductId(productOffer.getId());
        cartItemDto0.setQuantity(100);

        cartItemService.saveState(customer.getId(), cartItemArrayDto);
    }

    @Test
    @Transactional
    void testGetState(){
        CartItemArrayDto state = cartItemService.getState(1);

        Set<CartItemDto> cartItemDtoList = state.getCartItemDtoList();

        cartItemDtoList.forEach(cartItemDto -> {
            Assertions.assertEquals(100, cartItemDto.getQuantity());
        });
    }

    @Test
    void testGet(){
    }
}
