package ru.teamtwo.website.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.core.dtos.user.CustomerDto;
import ru.teamtwo.core.models.user.Customer;
import ru.teamtwo.website.service.user.CustomerService;

@Slf4j
@RestController
@RequestMapping("/marketplace/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("{id}")
    public CustomerDto get(@PathVariable Integer id){
        return customerService.getId(id);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<?> post(@RequestBody CustomerDto dto){
        try {
            Customer customer = customerService.addCustomer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer.getId());
        }
        catch(Exception e){
            log.debug("error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
