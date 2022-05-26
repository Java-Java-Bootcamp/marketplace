package ru.teamtwo.api.service.impl.customer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.datasource.init.ScriptParseException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.teamtwo.api.BaseTestEntities;
import ru.teamtwo.api.exception.ServerRuntimeException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.mappers.customer.CustomerMapper;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.core.dtos.customer.CustomerDto;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
    @Autowired
    BaseTestEntities baseTestEntities;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerMapper realCustomerMapper;
    private CustomerDto customerDto;
    private Customer customer;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customer = baseTestEntities.getCustomer();
        customerDto = realCustomerMapper.convert(customer);
    }

    @Test
    void get() {
        when(customerRepository.existsById(customer.getId())).thenReturn(false);
        when(customerMapper.convert(customer)).thenReturn(customerDto);
        when(customerMapper.convert(Mockito.any(CustomerDto.class))).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        assertThat(customerService.get(customer.getId())).isEqualTo(customerDto);
        verify(customerRepository).existsById(customer.getId());
        verify(customerRepository).save(customer);

        reset(customerRepository, customerMapper);

        when(customerRepository.existsById(customer.getId())).thenReturn(true);
        when(customerRepository.getById(customer.getId())).thenReturn(customer);
        when(customerMapper.convert(customer)).thenReturn(customerDto);
        assertThat(customerService.get(customer.getId())).isEqualTo(customerDto);
        verify(customerRepository).existsById(customer.getId());
        verify(customerRepository).getById(customer.getId());
        verify(customerMapper).convert(customer);
    }

    @Test
    void save() {
        when(customerMapper.convert(customerDto)).thenReturn(customer);
        when(customerRepository.saveAll(any())).thenThrow(RuntimeException.class);
        assertThatThrownBy(()-> {
            customerService.save(Collections.singleton(customerDto));
            verify(customerMapper).convert(customerDto);
            verify(customerRepository).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(customer)));
        }).isInstanceOf(ServerRuntimeException.class);

        reset(customerRepository, customerMapper);

        when(customerMapper.convert(customerDto)).thenReturn(customer);
        when(customerRepository.saveAll(any())).thenThrow(ScriptParseException.class);
        assertThatThrownBy(()-> {
            customerService.save(Collections.singleton(customerDto));
            verify(customerMapper).convert(customerDto);
            verify(customerRepository).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(customer)));
        }).isInstanceOf(UnableToAddItemException.class);

        reset(customerRepository, customerMapper);

        when(customerMapper.convert(customerDto)).thenReturn(customer);
        when(customerRepository.saveAll(MockitoHamcrest.argThat(Matchers.hasItems(customer)))).thenReturn(Collections.singletonList(customer));
        assertThat(customerService.save(Collections.singleton(customerDto))).isEqualTo(Collections.singleton(customer.getId()));
        verify(customerMapper).convert(customerDto);
        verify(customerRepository).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(customer)));
    }
}