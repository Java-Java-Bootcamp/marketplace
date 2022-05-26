package ru.teamtwo.api.mappers.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.customer.CustomerDto;

import static ru.teamtwo.api.TestUtils.SPECIFIC_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_STRING;
import static ru.teamtwo.api.TestUtils.assertDtoAndEntityEqual;

@DataJpaTest
class CustomerMapperTest {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductOfferRepository productOfferRepository;
    Customer customer;
    CustomerDto customerDto;
    @BeforeEach
    void setUp() {
        customer = customerRepository.save(new Customer(
                SPECIFIC_ID,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_NUMBER,
                UNIMPORTANT_NUMBER,
                UNIMPORTANT_ID
        ));
        customerDto = new CustomerDto(customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getStage(),
                customer.getSearchQuery(),
                customer.getSortingTypeField(),
                customer.getSortingTypeAscDesc(),
                customer.getOffset(),
                customer.getLimit(),
                customer.getCurrentProductId());
    }

    @Test
    void convertToDto() {
        CustomerDto convert = customerMapper.convert(customer);
        assertDtoAndEntityEqual(convert, customer);
    }

    @Test
    void convertFromDto() {
        Customer convert = customerMapper.convert(customerDto);
        assertDtoAndEntityEqual(customerDto, convert);
    }
}