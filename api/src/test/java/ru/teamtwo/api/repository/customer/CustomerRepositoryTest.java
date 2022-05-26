package ru.teamtwo.api.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import ru.teamtwo.api.models.customer.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.teamtwo.api.TestUtils.ANOTHER_NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.EMPTY_ID;
import static ru.teamtwo.api.TestUtils.NEW_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_NUMBER;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_STRING;

@DataJpaTest
class CustomerRepositoryTest {
    final Long ID = 100L;
    @Autowired
    CustomerRepository customerRepository;
    Customer customer;
    Customer setupCustomer;
    @BeforeEach
    void setUp() {
        setupCustomer = new Customer(ID,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_NUMBER,
                UNIMPORTANT_NUMBER,
                UNIMPORTANT_ID);
        setupCustomer = customerRepository.save(setupCustomer);
    }

    @Test
    void get(){
        assertThat(customerRepository.existsById(EMPTY_ID)).isFalse();
        assertThat(customerRepository.existsById(setupCustomer.getId())).isTrue();
        assertThat(customerRepository.getById(setupCustomer.getId())).isEqualTo(setupCustomer);
    }

    @Test
    void save() {
        //null fields
        customer = new Customer();
        assertThatThrownBy(()->customerRepository.save(customer)).isInstanceOf(DataAccessException.class);

        //basic save
        customer = new Customer(ID,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_NUMBER,
                UNIMPORTANT_NUMBER,
                UNIMPORTANT_ID);
        Long id = customerRepository.save(customer).getId();

        //overwrite
        customer = new Customer(ID,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_STRING,
                UNIMPORTANT_NUMBER,
                NEW_NUMBER,
                UNIMPORTANT_ID);
        Customer newCustomer = customerRepository.save(customer);
        assertThat(newCustomer.getId()).isEqualTo(id);
        assertThat(newCustomer.getLimit()).isEqualTo(NEW_NUMBER);

        //edit and save
        newCustomer.setLimit(ANOTHER_NEW_NUMBER);
        newCustomer = customerRepository.save(newCustomer);
        assertThat(newCustomer.getLimit()).isEqualTo(ANOTHER_NEW_NUMBER);
    }
}