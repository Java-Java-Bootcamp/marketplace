package ru.teamtwo.core.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItemDto implements Serializable {
    private Integer id;
    private Integer orderId;
    private Integer productOfferId;
    private Integer quantity;
}
