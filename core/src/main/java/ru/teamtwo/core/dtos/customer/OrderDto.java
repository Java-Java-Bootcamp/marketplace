package ru.teamtwo.core.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.core.models.customer.Order;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    private Integer id;
    private Integer customerId;
    //private LocalDate createdOn;

    public OrderDto(Order entity) {
        this.id = entity.getId();
        this.customerId = entity.getCustomer().getId();
        //this.createdOn = entity.getCreatedOn();
    }
}
