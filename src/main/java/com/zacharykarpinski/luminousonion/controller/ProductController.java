package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Product;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.repository.ProductRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import com.zacharykarpinski.luminousonion.response.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    SourceRepository sourceRepository;

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseHandler.createResponse("entity deleted",HttpStatus.OK);
        }
        return ResponseHandler.createResponse("ID not found",HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @GetMapping("/{id}/findings/summary")
    public ResponseEntity<Object> getFindingSummary(@PathVariable Long id) {
        List<List<Object>> summary = productRepository.getProductFindingsCountSummary(id);
        Map<String, Integer> map = summary.stream().collect(toMap(i ->
                { if (i.get(0) == null) {
                    return "UNKNOWN";
                }
                return i.get(0).toString();
        }, i -> (int) i.get(1) ));
        return ResponseHandler.createResponse("Ok",HttpStatus.OK,map);
    }

    @CrossOrigin
    @GetMapping("/{id}/sources")
    public ResponseEntity<List<Source>> getProductSources(@PathVariable Long id) {
        return ResponseEntity.ok(sourceRepository.getSourcesByProduct(id));
    }

}
