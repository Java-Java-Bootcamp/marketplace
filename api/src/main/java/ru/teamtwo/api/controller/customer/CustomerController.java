package ru.teamtwo.api.controller.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.service.customer.CustomerService;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.models.customer.Customer;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/customer")
public class CustomerController {
    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("{id}")
    public CustomerDto get(@PathVariable Integer id) {
        try {
            return customerService.getId(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Can't get customer " + id);
        }
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody CustomerDto dto){
        try {
            Customer customer = customerService.addCustomer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer.getId());
        }
        catch(Exception e){
            throw new UnableToAddItemException("Unable to add customer " + dto.toString());
        }
    }
}
