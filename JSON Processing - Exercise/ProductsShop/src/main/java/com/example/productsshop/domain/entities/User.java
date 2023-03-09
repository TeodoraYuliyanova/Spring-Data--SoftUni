package com.example.productsshop.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column
    private int age;

    @OneToMany(targetEntity = Product.class,mappedBy = "seller")
    private Set<Product> soldProducts;

    @OneToMany(targetEntity = Product.class,mappedBy = "buyer")
    private Set<Product> boughtProducts;

    @ManyToMany(cascade = {
            CascadeType.DETACH, CascadeType.MERGE
    })
    @Fetch(FetchMode.JOIN)
    private Set<User> friends;

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

    public User(String firstName, String lastName, int age, Set<Product> soldProducts, Set<Product> boughtProducts, Set<User> friends) {
        this.firstName = firstName;
        setLastName(lastName);
        this.age = age;
        this.soldProducts = soldProducts;
        this.boughtProducts = boughtProducts;
        this.friends = friends;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName.length() < 3){
            throw new IllegalArgumentException("Last name should be at least 3 characters.");
        }
        this.lastName = lastName;
    }

    public void setAge(int age) {
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
