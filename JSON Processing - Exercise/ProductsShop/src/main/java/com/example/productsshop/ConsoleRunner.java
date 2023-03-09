package com.example.productsshop;

import com.example.productsshop.service.CategoryService.CategoryService;
import com.example.productsshop.service.ProductService.ProductService;
import com.example.productsshop.service.SeedService.SeedService;
import com.example.productsshop.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //this.seedService.seedUsers();
        //this.seedService.seedCategories();
        //this.seedService.seedProducts();
        //this.productService.findAllByPriceBetweenAndBuyerIsNotNullOrderByPriceDesc(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));
       //this.userService.findAllBySoldProductsIsNotNullAndSoldProductsBuyerLastNameIsNotNullOrderBySoldProductsBuyerFirstName();
       // this.categoryService.getCategoriesSummary();
    }
}
