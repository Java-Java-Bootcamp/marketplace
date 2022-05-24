package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.mappers.customer.CustomerMapper;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.service.api.customer.CustomerService;
import ru.teamtwo.core.dtos.customer.CustomerDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto get(Long id) {
        if(customerRepository.existsById(id))
            return customerMapper.convert(customerRepository.getById(id));
        else
            throw new ItemNotFoundException();
    }

    @Override
    public Set<Long> save(Set<CustomerDto> dtos) {
        return customerRepository
                .saveAll(dtos.stream().map(customerMapper::convert).collect(Collectors.toSet()))
                .stream()
                .map(Customer::getId)
                .collect(Collectors.toSet());
    }
}
