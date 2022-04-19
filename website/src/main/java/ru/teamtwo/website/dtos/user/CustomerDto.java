package ru.teamtwo.website.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.website.model.user.Customer;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
    private Integer id;
    private String name;
    private String address;

    public CustomerDto(Customer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
    }
}
