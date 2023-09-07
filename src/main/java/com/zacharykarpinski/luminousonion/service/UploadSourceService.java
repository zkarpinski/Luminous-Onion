package com.zacharykarpinski.luminousonion.service;

import com.zacharykarpinski.luminousonion.model.Product;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import com.zacharykarpinski.luminousonion.parser.Grype;
import com.zacharykarpinski.luminousonion.parser.Trivy;
import com.zacharykarpinski.luminousonion.parser.*;
import com.zacharykarpinski.luminousonion.repository.ProductRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadSourceService {

    private ProductRepository productRepository;

    @Autowired
    public UploadSourceService(ProductRepository repo) {
        this.productRepository = repo;
    }

    public Source uploadFileAndParse(MultipartFile mpf, String label, SourceTool tool, Long productId, boolean archivePrevious) {

        // Get the product entity
        Product product = productRepository.findById(productId).orElseThrow();

        // Parse the file based on the provided tool
        Source parsedSource = switch (tool) {
            case ANCORE_GRYPE -> Grype.parse(mpf);
            case AQUA_TRIVY -> Trivy.parse(mpf);
            case DOCKER_SCOUT -> Sarif.parse(mpf);
            //case OTHER_EXTERNAL, OTHER_INTERNAL -> new Source(tool);
            default -> null;
        };

        // Archive all similar sources from the same product
        if (archivePrevious) {
            product.getSources().stream()
                    .filter(source -> source.getTool() == tool && !source.isArchived())
                    .forEach( s -> s.setArchived(true));
        }

        // Add the product and save to repository
        if (parsedSource != null) {
            parsedSource.setLabel(label);
            product.addSource(parsedSource);
            productRepository.save(product);
            return parsedSource;
        }


        return null;
    }

}
