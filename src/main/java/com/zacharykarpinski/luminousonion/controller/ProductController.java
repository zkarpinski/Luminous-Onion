package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Product;
import com.zacharykarpinski.luminousonion.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @CrossOrigin
    @GetMapping("/api/product")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @CrossOrigin
    @PostMapping("/api/product")
    public ResponseEntity<Product> putNewProduct(@RequestBody Product product) {
        return new ResponseEntity<Product>( productRepository.save(product), HttpStatus.CREATED);
    }
}
