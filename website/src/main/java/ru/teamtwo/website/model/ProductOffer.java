package ru.teamtwo.website.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

/*GET /product-offers?text={text}

        response: List Of ProductOffer

        ProductOffer:
        product:
        category: text
        model: text
        manufacturer: text
        description: text
        rate: numeric
        store:
        name: text
        rate: numeric
        available: numeric*/
@Getter
@Setter
@NoArgsConstructor
@Entity

public class ProductOffer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(cascade = {CascadeType.ALL})
    private Product product;
    @OneToOne(cascade = {CascadeType.ALL})
    private Store store;
    private long available;
}


