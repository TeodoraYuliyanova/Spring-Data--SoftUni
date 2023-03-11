package com.example.productsshopxml.repositories;

import com.example.productsshopxml.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "SELECT * FROM `products_shop`.categories ORDER BY RAND() LIMIT 1",nativeQuery = true)
    Optional<Category> getRandomEntity();
}
