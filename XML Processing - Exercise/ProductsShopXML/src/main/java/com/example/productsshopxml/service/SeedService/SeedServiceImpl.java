package com.example.productsshopxml.service.SeedService;

import com.example.productsshopxml.domain.dtos.categories.wrappers.ImportCategoriesWrapperDTO;
import com.example.productsshopxml.domain.dtos.products.wrappers.ImportProductsWrapperDTO;
import com.example.productsshopxml.domain.dtos.users.wrappers.ImportUsersWrapperDTO;
import com.example.productsshopxml.domain.entities.Category;
import com.example.productsshopxml.domain.entities.Product;
import com.example.productsshopxml.domain.entities.User;
import com.example.productsshopxml.repositories.CategoryRepository;
import com.example.productsshopxml.repositories.ProductRepository;
import com.example.productsshopxml.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws JAXBException, IOException {
        if (this.userRepository.count() == 0) {

            final FileReader fileReader = new FileReader("src/main/resources/dbContent/users.xml");

            final JAXBContext jaxbContext = JAXBContext.newInstance(ImportUsersWrapperDTO.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            final ImportUsersWrapperDTO importUserDTO = (ImportUsersWrapperDTO) unmarshaller.unmarshal(fileReader);

            final List<User> users = importUserDTO.getUsers().stream()
                    .map(importUser -> modelMapper.map(importUser, User.class)).toList();

            this.userRepository.saveAllAndFlush(users);
            fileReader.close();
        }
    }

    @Override
    public void seedCategories() throws JAXBException, IOException {
        if (this.categoryRepository.count() == 0) {

            final FileReader fileReader = new FileReader("src/main/resources/dbContent/categories.xml");

            final JAXBContext context = JAXBContext.newInstance(ImportCategoriesWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

             final ImportCategoriesWrapperDTO importCategoriesWrapperDTO = (ImportCategoriesWrapperDTO) unmarshaller.unmarshal(fileReader);

            final List<Category> categories = importCategoriesWrapperDTO.getCategory().stream()
                    .map(importCategoryDTO -> modelMapper.map(importCategoryDTO, Category.class)).toList();

            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();

        }
    }

    @Override
    public void seedProducts() throws JAXBException, IOException {
        if (this.productRepository.count() == 0){

            final FileReader fileReader = new FileReader("src/main/resources/dbContent/products.xml");

            final JAXBContext context = JAXBContext.newInstance(ImportProductsWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final ImportProductsWrapperDTO productsWrapperDTO = (ImportProductsWrapperDTO) unmarshaller.unmarshal(fileReader);

            List<Product> products = productsWrapperDTO.getProduct().stream()
                    .map(importProductDTO -> modelMapper.map(importProductDTO, Product.class))
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
