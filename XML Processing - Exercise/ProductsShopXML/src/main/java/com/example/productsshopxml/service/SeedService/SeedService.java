package com.example.productsshopxml.service.SeedService;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {

    void seedUsers() throws JAXBException, IOException;
    void seedCategories() throws JAXBException, IOException;
    void seedProducts() throws JAXBException, IOException;
}
