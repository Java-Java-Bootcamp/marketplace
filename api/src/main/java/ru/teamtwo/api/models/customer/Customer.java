package ru.teamtwo.api.models.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamtwo.api.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer", schema = "marketplace")
public class Customer implements BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "\"name\"", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Lob
    @Column(name = "stage")
    private String stage;

    @Lob
    @Column(name = "search_query")
    private String searchQuery;

    @Lob
    @Column(name = "sorting_type_field")
    private String sortingTypeField;

    @Lob
    @Column(name = "sorting_type_asc_desc")
    private String sortingTypeAscDesc;

    @Column(name = "\"offset\"")
    private Integer offset;

    @Column(name = "\"limit\"")
    private Integer limit;

    @Column(name = "current_product_id")
    private Long currentProductId;
}