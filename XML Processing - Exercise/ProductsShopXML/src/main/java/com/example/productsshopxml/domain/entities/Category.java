package com.example.productsshopxml.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Column(nullable = false)
    private String name;

    public Category(String name) {
        setName(name);
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

    public void setName(String name) {
        if (name.length() < 3 || name.length() > 15){
            throw new IllegalArgumentException("Name must be between 3 and 15 characters.");
        }
        this.name = name;
    }
}
