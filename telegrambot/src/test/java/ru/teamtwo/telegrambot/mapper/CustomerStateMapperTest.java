package ru.teamtwo.telegrambot.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.teamtwo.core.dtos.customer.CartItemDto;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.telegrambot.CustomerStateTestUtils;
import ru.teamtwo.telegrambot.model.customer.CustomerOrder;
import ru.teamtwo.telegrambot.model.customer.CustomerState;

import java.util.HashSet;

@SpringBootTest
class CustomerStateMapperTest {
    @Autowired
    CustomerStateMapper customerStateMapper;
    CustomerState customerState;
    CustomerDto customerDto;
    @BeforeEach
    void setUp() {
        customerState = CustomerStateTestUtils.getCustomerState();
        customerDto = CustomerStateTestUtils.getCustomerDto();
    }

    @Test
    void convertToDto() {
        CustomerDto convert = customerStateMapper.convert(customerState);
        Assertions.assertThat(convert.id()).isEqualTo(customerState.getUserId());
        Assertions.assertThat(convert.name()).isEqualTo(customerState.getName());
        Assertions.assertThat(convert.address()).isEqualTo(customerState.getAddress());
        Assertions.assertThat(convert.stage()).isEqualTo(customerState.getStage().toString());
        Assertions.assertThat(convert.searchQuery()).isEqualTo(customerState.getSearchQuery());
        Assertions.assertThat(convert.sortingTypeField()).isEqualTo(customerState.getSortingTypeField().toString());
        Assertions.assertThat(convert.sortingTypeAscDesc()).isEqualTo(customerState.getSortingTypeAscDesc().toString());
        Assertions.assertThat(convert.offset()).isEqualTo(customerState.getOffset());
        Assertions.assertThat(convert.limit()).isEqualTo(customerState.getLimit());
        Assertions.assertThat(convert.currentProductId()).isEqualTo(customerState.getCurrentProductId());
    }

    @Test
    void convertFromDto() {
        HashSet<CartItemDto> cart = new HashSet<>();
        HashSet<CustomerOrder> orders = new HashSet<>();
        CustomerState convert = customerStateMapper.convert(customerDto, cart, orders);
        Assertions.assertThat(customerDto.id()).isEqualTo(convert.getUserId());
        Assertions.assertThat(customerDto.name()).isEqualTo(convert.getName());
        Assertions.assertThat(customerDto.address()).isEqualTo(convert.getAddress());
        Assertions.assertThat(customerDto.stage()).isEqualTo(convert.getStage().toString());
        Assertions.assertThat(customerDto.searchQuery()).isEqualTo(convert.getSearchQuery());
        Assertions.assertThat(customerDto.sortingTypeField()).isEqualTo(convert.getSortingTypeField().toString());
        Assertions.assertThat(customerDto.sortingTypeAscDesc()).isEqualTo(convert.getSortingTypeAscDesc().toString());
        Assertions.assertThat(customerDto.offset()).isEqualTo(convert.getOffset());
        Assertions.assertThat(customerDto.limit()).isEqualTo(convert.getLimit());
        Assertions.assertThat(cart).containsAll(convert.getCart());
        Assertions.assertThat(orders).containsAll(convert.getOrders());
    }
}