package ru.teamtwo.backend.mappers.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.backend.models.customer.Customer;
import ru.teamtwo.backend.repository.customer.CustomerRepository;
import ru.teamtwo.backend.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.customer.CustomerDto;

import static ru.teamtwo.backend.TestUtils.SPECIFIC_ID;
import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_NUMBER;
import static ru.teamtwo.backend.TestUtils.UNIMPORTANT_STRING;
import static ru.teamtwo.backend.TestUtils.assertDtoAndEntityEqual;

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
        CustomerDto convert = customerMapper.convertToDto(customer);
        assertDtoAndEntityEqual(convert, customer);
    }

    @Test
    void convertFromDto() {
        Customer convert = customerMapper.convertToEntity(customerDto);
        assertDtoAndEntityEqual(customerDto, convert);
    }
}