package com.example.productsshopxml;

import com.example.productsshopxml.service.ProductService.ProductService;
import com.example.productsshopxml.service.SeedService.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;

    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService) {
        this.seedService = seedService;

        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        //this.seedService.seedUsers();
       // this.seedService.seedCategories();
       //this.seedService.seedProducts();
        this.productService.findAllByPriceBetweenAndBuyerNotNullOrderByPrice(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));
    }
}
