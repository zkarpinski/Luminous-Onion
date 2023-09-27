package com.luminousonion.service;

import com.luminousonion.model.Product;
import com.luminousonion.model.Source;
import com.luminousonion.parser.Grype;
import com.luminousonion.parser.Sarif;
import com.luminousonion.parser.Trivy;
import com.luminousonion.model.shared.SourceTool;
import com.luminousonion.repository.ProductRepository;
import com.luminousonion.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadSourceService {

    private ProductRepository productRepository;
    private SourceRepository sourceRepository;

    @Autowired
    public UploadSourceService(ProductRepository repo,
                               SourceRepository sourceRepository) {
        this.productRepository = repo;
        this.sourceRepository = sourceRepository;
    }

    public Source uploadFileAndParse(MultipartFile mpf, String label, SourceTool tool, Long productId, boolean archivePrevious) {

        // Get the product entity
        Product product = productRepository.findById(productId).orElseThrow();

        // Parse the file based on the provided tool
        Source parsedSource = switch (tool) {
            case ANCORE_GRYPE -> Grype.parse(mpf);
            case AQUA_TRIVY -> Trivy.parse(mpf);
            case DOCKER_SCOUT -> Sarif.parse(mpf);
            default -> null;
        };

        // Add the product and save to repository
        if (parsedSource != null) {
            parsedSource.setLabel(label);

            parsedSource = sourceRepository.saveAndFlush(parsedSource);

            // Archive all similar sources from the same product
            if (archivePrevious) {
                product.getSources().stream()
                    .filter(source -> source.getTool() == tool && !source.isArchived())
                    .forEach( s -> s.setArchived(true));
            }
            product.addSource(parsedSource);
            productRepository.saveAndFlush(product);
            return parsedSource;
        }


        return null;
    }

}
