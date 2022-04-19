package ru.teamtwo.website.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.website.model.user.Order;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    private Integer id;
    private LocalDate createdOn;
    public OrderDto(Order entity){
        this.id = entity.getId();
        this.createdOn = entity.getCreatedOn();
    }
}
