package com.example.productsshop.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User buyer;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User seller;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories;


    public Product(String name, BigDecimal price, User buyer, User seller, Set<Category> categories) {
        setName(name);
        this.price = price;
        this.buyer = buyer;
        this.seller = seller;
        this.categories = categories;
    }

    public void setName(String name) {
        if (name.length() < 3){
            throw new IllegalArgumentException("Name should be at least 3 characters.");
        }
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
