package ru.teamtwo.website.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.teamtwo.core.dtos.user.CustomerDto;
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
            return customerService.addCustomer(dto);
        }
}
