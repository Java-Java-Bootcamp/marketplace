package ru.teamtwo.core.mappers.customer;

import org.mapstruct.Mapper;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.core.models.customer.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto convert(Customer customer);
    Customer convert(CustomerDto customerDto);
}
