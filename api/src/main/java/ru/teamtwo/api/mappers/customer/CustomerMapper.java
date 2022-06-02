package ru.teamtwo.api.mappers.customer;

import org.mapstruct.Mapper;
import ru.teamtwo.api.mappers.BaseMapper;
import ru.teamtwo.api.models.customer.Customer;
import ru.teamtwo.core.dtos.customer.CustomerDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends BaseMapper<Customer, CustomerDto> {
}
