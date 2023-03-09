package com.example.productsshop.repositories;

import com.example.productsshop.domain.dtos.categories.CategoryByProductsSummaryDto;
import com.example.productsshop.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "SELECT * FROM `products_shop`.categories ORDER BY RAND() LIMIT 1",nativeQuery = true)
    Optional<Category> getRandomEntity();


    @Query("SELECT NEW com.example.productsshop.domain.dtos.categories.CategoryByProductsSummaryDto" +
            "(c.name,count(p.id),avg(p.price),sum(p.price)) " +
            "from Product p " +
            "join p.categories c " +
            "group by c.id " +
            "order by count(p.id) DESC")
    Optional<List<CategoryByProductsSummaryDto>> getCategoriesSummary();
}
