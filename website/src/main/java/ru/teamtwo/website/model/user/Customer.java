package ru.teamtwo.website.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.teamtwo.website.dtos.user.CustomerDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customer", schema = "marketplace")
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public Customer(CustomerDto dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.address = dto.getAddress();
    }
}