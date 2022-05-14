package ru.teamtwo.core.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto implements Serializable {
    private Integer id;
    private Integer customerId;
    private LocalDate createdOn;
}
