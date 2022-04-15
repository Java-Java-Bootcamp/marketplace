package ru.teamtwo.telegrambot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private String manufacturer;
    private String description;
    private String category;
    private double rating;
    private double price;
    private String sellerName;
    private int sellerRating;
    private int available;

    public String toString() {
        int var10000 = this.getId();
        return  " Название: " + this.getName() + ",\n" +
                " Производитель: " + this.getManufacturer() + ",\n" +
                " Описание: " + this.getDescription() + ",\n" +
                " Категория: " + this.getCategory() + ",\n" +
                " Рейтинг товара: " + this.getRating() + ",\n" +
                " Стоимость: " + this.getPrice() + ",\n" +
                " Продавец: " + this.getSellerName() + ",\n" +
                " Рейтинг продавца: " + this.getSellerRating() + ",\n" +
                " Осталось: " + this.getAvailable();
    }
}
