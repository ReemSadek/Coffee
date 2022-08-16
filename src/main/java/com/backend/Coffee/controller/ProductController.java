package com.backend.Coffee.controller;

import com.backend.Coffee.model.Product;
import com.backend.Coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;
    //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/all")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }


    @PostMapping("/add")
    //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Product createNewGateway(@RequestBody Product product) {
        return productService.createNewProduct(product);
    }

    @DeleteMapping("/del/{id}")
    public String createNewGateway(@PathVariable Long id) {
        return productService.deleteById(id);
    }

}