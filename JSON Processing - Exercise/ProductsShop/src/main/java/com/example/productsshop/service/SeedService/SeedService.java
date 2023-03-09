package com.example.productsshop.service.SeedService;

import java.io.IOException;

public interface SeedService {

    void seedUsers() throws IOException;
    void seedCategories() throws IOException;
    void seedProducts() throws IOException;

}
