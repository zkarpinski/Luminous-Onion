package com.zacharykarpinski.luminousonion.service;

import com.zacharykarpinski.luminousonion.model.Product;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.SourceTool;
import com.zacharykarpinski.luminousonion.parsers.*;
import com.zacharykarpinski.luminousonion.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class UploadSourceService {

    @Autowired
    private ProductRepository productRepository;

    public Source uploadFileAndParse(MultipartFile mpf, SourceTool tool, Long productId) throws IOException {

        Product product = productRepository.findById(productId).orElseThrow();

        // Parse the file based on the provided tool
        Source parsedSource = switch (tool) {
            case ANCORE_GRYPE -> Grype.parse(mpf);
            case AQUA_TRIVY -> Trivy.parse(mpf);
            default -> null;
        };

        // Add the product and save to repository
        if (parsedSource != null && product !=null) {
            product.addSource(parsedSource);
            productRepository.save(product);
            return parsedSource;
        }

        return null;
    }

}
