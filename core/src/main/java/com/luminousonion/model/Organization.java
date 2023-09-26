package com.luminousonion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid; // TODO make this the primary ID
    private String name;


    // Date and times
    @CreationTimestamp
    private Date createTimestamp;
    @UpdateTimestamp
    private Date lastUpdateTimestamp;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    @JsonIgnore
    public Set<Product> getProducts() {
        return this.products;
    }

    public void addSource(Product product) {
        if (products == null) {
            this.products = new HashSet<>();
        }
        product.setOrg(this);
        this.products.add(product);
    }


    // Constructors
    public Organization() {}

    public Organization(int id) {
        this.id=id;
    }

}
