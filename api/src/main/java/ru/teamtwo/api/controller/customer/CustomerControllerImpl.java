package ru.teamtwo.api.controller.customer;

import lombok.RequiredArgsConstructor;
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
import ru.teamtwo.api.service.api.customer.CustomerService;
import ru.teamtwo.core.dtos.controller.customer.CustomerController;
import ru.teamtwo.core.dtos.customer.CustomerDto;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
@RequestMapping("/marketplace/api/customer")
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.get(id));
    }

    @Override
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Set<Long>> save(@RequestBody Set<CustomerDto> dtos){
        return ResponseEntity.ok(customerService.save(dtos));
    }
}
