package ru.teamtwo.api.service.impl.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.teamtwo.api.BaseTestEntitiesImpl;
import ru.teamtwo.api.mappers.customer.CustomerMapper;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.service.impl.ServiceTestUtils;
import ru.teamtwo.api.service.impl.ServiceTestUtilsParams;
import ru.teamtwo.core.dtos.customer.CustomerDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
class CustomerServiceImplTest {
    @Autowired
    BaseTestEntitiesImpl baseTestEntities;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    private CustomerDto customerDto;
    private Customer customer;
    @InjectMocks
    private CustomerServiceImpl customerService;
    private ServiceTestUtilsParams serviceTestUtilsParams;

    @BeforeEach
    void setUp() {
        customer = baseTestEntities.getCustomer();
        customerDto = baseTestEntities.getCustomerDto();
        serviceTestUtilsParams = new ServiceTestUtilsParams(
                customerService,
                customerRepository,
                customer,
                customerDto,
                customerMapper);
    }

    @Test
    void get() {
        when(customerRepository.existsById(customer.getId())).thenReturn(false);
        when(customerMapper.convertToDto(customer)).thenReturn(customerDto);
        when(customerMapper.convertToEntity(Mockito.any(CustomerDto.class))).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        assertThat(customerService.get(customer.getId())).isEqualTo(customerDto);
        verify(customerRepository).existsById(customer.getId());
        verify(customerRepository).save(customer);

        reset(customerRepository, customerMapper);

        when(customerRepository.existsById(customer.getId())).thenReturn(true);
        when(customerRepository.getById(customer.getId())).thenReturn(customer);
        when(customerMapper.convertToDto(customer)).thenReturn(customerDto);
        assertThat(customerService.get(customer.getId())).isEqualTo(customerDto);
        verify(customerRepository).existsById(customer.getId());
        verify(customerRepository).getById(customer.getId());
        verify(customerMapper).convertToDto(customer);
    }

    @Test
    void save() {
        ServiceTestUtils.testBasicSave(serviceTestUtilsParams);
    }
}