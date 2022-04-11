package ru.teamtwo.telegrambot.dtos;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OrderDTO {

    @Data
    public static class OrderDTOProduct{
        private String id;
        private int quantity;
    }

    private String address;
    private ArrayList<OrderDTOProduct> products;
}
