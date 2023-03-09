package com.example.gamestore.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {


    @OneToOne
    private User buyer;


    @ManyToMany
    private Set<Game> games;

}
