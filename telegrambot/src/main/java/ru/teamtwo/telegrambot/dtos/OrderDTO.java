package ru.teamtwo.telegrambot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDTOProduct{
        private String id;
        private int quantity;
    }

    private String address;
    private ArrayList<OrderDTOProduct> products;
}
