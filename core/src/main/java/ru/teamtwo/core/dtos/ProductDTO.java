package ru.teamtwo.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamtwo.core.models.ProductOffer;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
        this.price = productOffer.getProduct().getPrice();
        this.sellerName = productOffer.getStore().getName();
        this.sellerRating = productOffer.getStore().getRating();
        this.quantity = productOffer.getQuantity();
    }
}
