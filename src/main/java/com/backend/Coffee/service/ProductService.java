package com.backend.Coffee.service;


import com.backend.Coffee.model.Product;
import com.backend.Coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }


    public Product createNewProduct(Product product) {
        return productRepository.save(product);
    }

}