package com.example.productsshop.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;

    public Category(String name) {
        setName(name);
    }

    public void setName(String name) {
        if (name.length() < 3 || name.length() > 15){
            throw new IllegalArgumentException("Name should be between 3 and 15 characters.");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return Objects.equals(name, category.name)
                && Objects.equals(getId(),category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,getId());
    }
}
