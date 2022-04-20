package ru.teamtwo.core.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.core.models.user.Order;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    private Integer id;
    private Integer customerId;
    private LocalDate createdOn;

    public OrderDto(Order entity) {
        this.id = entity.getId();
        this.customerId = entity.getCustomer().getId();
        this.createdOn = entity.getCreatedOn();
    }
}
