package ru.teamtwo.website.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
// TODO : ask about Product field name out of the list in the task and sync with Alexandra about product structure
    private String name;
    private String category;
    private String model;
    private String manufacturer;
    private String description;
    private float rate;
}
