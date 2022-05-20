package ru.teamtwo.api.controller.customer;

import lombok.RequiredArgsConstructor;
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
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.api.service.api.customer.CustomerService;
import ru.teamtwo.core.dtos.controller.customer.CustomerController;
import ru.teamtwo.core.dtos.customer.CustomerDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace/api/customer")
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> get(@PathVariable Long id) {
        try {
            return customerService.getId(id);
        }
        catch (Exception e) {
            throw new ItemNotFoundException("Can't get customerId " + id);
        }
    }

    @Override
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody CustomerDto dto){
        try {
            Customer customer = customerService.addCustomer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer.getId());
        }
        catch(Exception e){
            throw new UnableToAddItemException("Unable to add customerId " + dto.toString());
        }
    }
}
