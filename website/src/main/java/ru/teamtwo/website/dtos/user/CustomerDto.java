package ru.teamtwo.website.dtos.user;

import lombok.Data;
import ru.teamtwo.website.model.user.Customer;

import java.io.Serializable;

@Data
public class CustomerDto implements Serializable {
    private final Integer id;
    private final String name;
    private final String address;

    public CustomerDto(Customer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
    }
}
