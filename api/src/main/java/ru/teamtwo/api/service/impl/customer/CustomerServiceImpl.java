package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.service.api.customer.CustomerService;
import ru.teamtwo.core.dtos.customer.CustomerDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDto get(Long id) {
        return new CustomerDto(customerRepository.getById(id));
    }

    @Override
    public Long save(CustomerDto dto) {
        return customerRepository.save(new Customer(dto));
    }
}
