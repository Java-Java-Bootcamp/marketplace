package ru.teamtwo.website.dtos.user;

import lombok.Data;
import ru.teamtwo.website.model.user.Order;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OrderDto implements Serializable {
    private final Integer id;
    private final LocalDate createdOn;
    public OrderDto(Order entity){
        this.id = entity.getId();
        this.createdOn = entity.getCreatedOn();
    }
}
