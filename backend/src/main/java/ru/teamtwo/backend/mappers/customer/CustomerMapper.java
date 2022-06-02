package ru.teamtwo.backend.mappers.customer;

import org.mapstruct.Mapper;
import ru.teamtwo.backend.mappers.BaseMapper;
import ru.teamtwo.backend.models.customer.Customer;
import ru.teamtwo.core.dtos.customer.CustomerDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends BaseMapper<Customer, CustomerDto> {
}
