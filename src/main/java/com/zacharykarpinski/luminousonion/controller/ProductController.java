package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Product;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Operation(summary = "Returns a list of products")
    @ApiResponse(responseCode = "200", description = "Product list returned")
    @CrossOrigin
    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<Product> putNewProduct(@RequestBody Product product) {
        return new ResponseEntity<>( productRepository.save(product), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productRepository.findById(id).orElse(null));
    }

    @CrossOrigin
    @GetMapping("/{id}/findings/summary")
    public ResponseEntity<Number> getFindingSummary(@PathVariable Long id) {
        return ResponseEntity.ok(productRepository.getProductFindingsCountSummary(id));
    }

    @CrossOrigin
    @GetMapping("/{id}/sources")
    public ResponseEntity<Source> getProductSources(@PathVariable Long id) {
        return null;
    }

}
