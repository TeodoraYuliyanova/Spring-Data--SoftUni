package com.example.productsshop.domain.dtos.users;

import com.example.productsshop.domain.entities.Product;
import com.example.productsshop.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String firstName;

    private String lastName;

    private int age;

    private Set<Product> soldProducts;

    private Set<Product> boughtProducts;

    private Set<User> friends;

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }
}
