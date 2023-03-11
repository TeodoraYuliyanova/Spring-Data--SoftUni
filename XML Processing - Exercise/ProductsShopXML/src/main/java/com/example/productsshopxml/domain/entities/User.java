package com.example.productsshopxml.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column
    private Integer age;

    @OneToMany(targetEntity = Product.class,mappedBy = "seller")
    private Set<Product> soldProducts;

    @OneToMany(targetEntity = Product.class,mappedBy = "buyer")
    private Set<Product> boughtProducts;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.MERGE
    })
    @Fetch(FetchMode.JOIN)
    private Set<User> friends;


    public User(String firstName, String lastName, Integer age, Set<Product> soldProducts, Set<Product> boughtProducts, Set<User> friends) {
        this.firstName = firstName;
        setLastName(lastName);
        this.age = age;
        this.soldProducts = soldProducts;
        this.boughtProducts = boughtProducts;
        this.friends = friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, getId());
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName.length() < 3){
            throw new IllegalArgumentException("Last name must be at least 3 characters.");
        }
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSoldProducts(Set<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public void setBoughtProducts(Set<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

}
