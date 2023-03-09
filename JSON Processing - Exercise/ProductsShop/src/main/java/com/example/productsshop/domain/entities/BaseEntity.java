package com.example.productsshop.domain.entities;

import lombok.Getter;

import javax.persistence.*;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
}
