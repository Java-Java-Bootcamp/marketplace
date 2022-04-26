package ru.teamtwo.website.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.models.customer.Customer;
import ru.teamtwo.website.repository.customer.CustomerRepository;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public CustomerDto getId(Integer id){
        log.debug("get: {}", id);
        return new CustomerDto(repository.getById(id));
    }

    public Customer addCustomer(CustomerDto dto){
        log.debug("post: {}", dto.toString());
        return repository.save(new Customer(dto));
    }
}
