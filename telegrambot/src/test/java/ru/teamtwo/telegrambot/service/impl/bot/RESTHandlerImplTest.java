package ru.teamtwo.telegrambot.service.bot.handlers;

class RESTHandlerImplTest {
/*
    static final Long USER_ID = 12345L;

    @Mock
    User user;
    Set<CartItemDto> testCart = new HashSet<>();
    Set<ProductDto> testQueryResult = new HashSet<>();
    CustomerState filledCustomerState;
    @Mock
    CustomerClient customerClient;
    @Mock
    ProductOfferClient productOfferClient;
    RESTHandler restHandler = new RESTHandlerImpl();

    @BeforeEach
    void setUp() {
        testCart.add(new CartItemDto(1, 1L, 1, 1));
        Mockito.when(user.getId()).thenReturn(USER_ID);

        filledCustomerState = CustomerState.builder()
                .address("Address")
                .user(user)
                .chatId("12345")
                .stage(Stage.WAITING_FOR_QUANTITY)
                .searchQuery("Search query")
                .sortingTypeField(ProductOfferController.SortingTypeField.PRODUCT_RATING)
                .sortingTypeAscDesc(ProductOfferController.SortingTypeAscDesc.ASC)
                .offset(5)
                .limit(10)
                .cart(testCart)
                .currentProductId(123)
                .queryResult(testQueryResult)
                .build();
    }

    @Test
    void getCustomerState() {
        Mockito.when(customerClient.get(user.getId())).thenReturn(filledCustomerState);

        CustomerState customerState = restHandler.getCustomerState(USER_ID);

        Mockito.verify(customerClient, Mockito.times(1)).get(user.getId());
        Assertions.assertThat(filledCustomerState);
    }

    @Test
    void saveCustomerState() {
        restHandler.saveCustomerState(filledCustomerState);

        Mockito.verify(customerClient, Mockito.times(1)).save();
    }

    @Test
    void queryProducts() {
        ProductSearchHandler.ProductQuery productQuery = new ProductSearchHandler.ProductQuery("query");

        ResponseEntity<Set<ProductOfferDto>> productQueryResult = restHandler.queryProducts(productQuery);

        Mockito.verify(customerClient, Mockito.times(1)).save();
    }

 */
}