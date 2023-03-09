package com.example.productsshop.service.SeedService;

import com.example.productsshop.domain.dtos.categories.ImportCategoriesDTO;
import com.example.productsshop.domain.dtos.products.ImportProductsDTO;
import com.example.productsshop.domain.dtos.users.ImportUsersDTO;
import com.example.productsshop.domain.entities.Category;
import com.example.productsshop.domain.entities.Product;
import com.example.productsshop.domain.entities.User;
import com.example.productsshop.repositories.CategoryRepository;
import com.example.productsshop.repositories.ProductRepository;
import com.example.productsshop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.example.productsshop.constants.Utils.GSON;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedUsers() throws IOException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader("src/main/resources/dbContent/users.json");

            final List<User> users = Arrays.stream(GSON.fromJson(fileReader, ImportUsersDTO[].class))
                    .map(importUsersDTO -> modelMapper.map(importUsersDTO, User.class)).toList();

            this.userRepository.saveAllAndFlush(users);
            fileReader.close();
        }
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader("src/main/resources/dbContent/categories.json");

            final List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, ImportCategoriesDTO[].class))
                    .map(importCategoriesDTO -> modelMapper.map(importCategoriesDTO, Category.class)).toList();

            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }


    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0) {

            final FileReader fileReader = new FileReader("src/main/resources/dbContent/products.json");

            final List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ImportProductsDTO[].class))
                    .map(importProductsDTO -> modelMapper.map(importProductsDTO, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategory)
                    .toList();

            this.productRepository.saveAllAndFlush(products);
            fileReader.close();
        }
    }

    private Product setRandomSeller(Product product) {
        final User seller = this.userRepository.getRandomEntity().orElseThrow(IllegalArgumentException::new);

        product.setSeller(seller);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
            User buyer = this.userRepository.getRandomEntity().orElseThrow(IllegalArgumentException::new);

            while (buyer.equals(product.getSeller())) {
                buyer = this.userRepository.getRandomEntity().orElseThrow(IllegalArgumentException::new);
            }

            product.setBuyer(buyer);
        }
        return product;

    }

    private Product setRandomCategory(Product product) {

        final Random random = new Random();

        final int numberOfCategories = random.nextInt((int) this.categoryRepository.count());

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < numberOfCategories; i++) {
            Category category = this.categoryRepository.getRandomEntity().orElseThrow(IllegalArgumentException::new);
            categories.add(category);
        }

        product.setCategories(categories);
        return product;
    }
}
