package ru.teamtwo.backend.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.backend.mappers.customer.CustomerMapper;
import ru.teamtwo.backend.models.customer.Customer;
import ru.teamtwo.backend.repository.customer.CustomerRepository;
import ru.teamtwo.backend.service.api.customer.CustomerService;
import ru.teamtwo.backend.service.impl.ServiceUtils;
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
        if (customerRepository.existsById(id))
            return customerMapper.convertToDto(customerRepository.getById(id));
        else {
            CustomerDto customerDto = new CustomerDto(
                    id,
                    "",
                    "",
                    "WAITING_FOR_SEARCH_START",
                    "",
                    "PRODUCT_NAME",
                    "DESC",
                    0,
                    5,
                    1L
            );
            return customerMapper.convertToDto(customerRepository.save(customerMapper.convertToEntity(customerDto)));
        }
    }

    @Override
    public Set<Long> save(Set<CustomerDto> dtos) {
        return ServiceUtils.save(() -> customerRepository
                .saveAll(dtos.stream().map(customerMapper::convertToEntity).collect(Collectors.toSet()))
                .stream()
                .map(Customer::getId)
                .collect(Collectors.toSet()), log);
    }
}
