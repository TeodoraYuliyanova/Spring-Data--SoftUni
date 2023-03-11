package com.example.productsshopxml.repositories;

import com.example.productsshopxml.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<List<Product>> findAllByPriceBetweenAndBuyerNotNullOrderByPrice(BigDecimal low,BigDecimal high);
}
