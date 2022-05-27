package ru.teamtwo.api.service.impl.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.api.exception.ServerRuntimeException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.logging.LoggingUtils;
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
        if (customerRepository.existsById(id))
            return customerMapper.convert(customerRepository.getById(id));
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
            return customerMapper.convert(customerRepository.save(customerMapper.convert(customerDto)));
        }
    }

    @Override
    public Set<Long> save(Set<CustomerDto> dtos) {
        try {
            return customerRepository
                    .saveAll(dtos.stream().map(customerMapper::convert).collect(Collectors.toSet()))
                    .stream()
                    .map(Customer::getId)
                    .collect(Collectors.toSet());
        } catch (DataAccessException e) {
            throw new UnableToAddItemException();
        } catch (Exception e) {
            LoggingUtils.logException(log, e);
            throw new ServerRuntimeException();
        }
    }
}
