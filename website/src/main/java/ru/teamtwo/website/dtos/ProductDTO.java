package ru.teamtwo.website.dtos;

import lombok.Data;
import ru.teamtwo.website.model.ProductOffer;

@Data
public class ProductDTO {
    private long id;
    private String name;
    private String manufacturer;
    private String description;
    private String category;
    private double rating;
    private double price;
    private String sellerName;
    private double sellerRating;
    private long quantity;

    public ProductDTO(ProductOffer productOffer) {
        this.id = productOffer.getId();
        this.name = productOffer.getProduct().getName();
        this.manufacturer = productOffer.getProduct().getManufacturer();
        this.description = productOffer.getProduct().getDescription();
        this.category = productOffer.getProduct().getCategory();
        this.rating = productOffer.getProduct().getRating();

        this.price = 0; //TODO: добавить в Product поле price

        this.sellerName = productOffer.getStore().getName();
        this.sellerRating = productOffer.getStore().getRating();
        this.quantity = productOffer.getQuantity();
    }
}
