package ru.teamtwo.api.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.models.customer.Customer;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerDto getId(Integer id){
        return new CustomerDto(repository.getById(id));
    }

    public Customer addCustomer(CustomerDto dto){
        log.debug("post: {}", dto.toString());
        return repository.save(new Customer(dto));
    }
}
