package ru.teamtwo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartItemServiceTests {
/*
    @Autowired
    CustomerRepository customerRepository;

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
        CartItemArrayDto stage = cartItemService.getState(1);

        Set<CartItemDto> cartItemDtoList = stage.getCartItemDtoList();

        cartItemDtoList.forEach(cartItemDto -> {
            Assertions.assertEquals(100, cartItemDto.getQuantity());
        });
    }

    @Test
    void testGet(){
    }

 */
}
