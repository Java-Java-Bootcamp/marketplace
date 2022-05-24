package ru.teamtwo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerServiceTests {
/*
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Transactional
    void testAddUser(){
        int CUSTOMER_ID = 2;

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(CUSTOMER_ID);
        customerDto.setName("name");
        customerDto.setAddress("address");

        List<Customer> customers = customerRepository.findAll();
        customers.forEach(customer -> {
            Assertions.assertNotEquals(CUSTOMER_ID, customer.getId());
        });

        Customer customer = customerService.addCustomer(customerDto);
        CustomerDto customerDtoGetId = customerService.getId(CUSTOMER_ID);

        Assertions.assertEquals(customer.getId(), customerDtoGetId.getId());
        Assertions.assertEquals(customer.getAddress(), customerDtoGetId.getAddress());
        Assertions.assertEquals(customer.getName(), customerDtoGetId.getName());
    }

 */
}
