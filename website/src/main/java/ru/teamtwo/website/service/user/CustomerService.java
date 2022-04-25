package ru.teamtwo.website.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.teamtwo.core.dtos.user.CustomerDto;
import ru.teamtwo.core.models.user.Customer;
import ru.teamtwo.website.repository.user.CustomerRepository;

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
