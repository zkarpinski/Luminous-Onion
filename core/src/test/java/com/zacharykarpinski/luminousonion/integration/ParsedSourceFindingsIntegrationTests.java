package com.zacharykarpinski.luminousonion.integration;

import com.zacharykarpinski.luminousonion.LuminousOnionApplication;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Product;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.repository.ProductRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import com.zacharykarpinski.luminousonion.service.UploadSourceService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = LuminousOnionApplication.class)
@Transactional
public class ParsedSourceFindingsIntegrationTests {

    @Autowired
    FindingRepository findingRepository;
    @Autowired
    SourceRepository sourceRepository;
    @Autowired
    ProductRepository productRepository;
    @Resource(name = "uploadSourceService")
    UploadSourceService uploadSourceService;

    @Test
    void givenValidJsonFile_whenUploadedThroughService_thenHaveFindings() throws Exception {
        SourceTool sourceTool = SourceTool.DOCKER_SCOUT;
        String sourceLabel = "Testing";

        // Create test product
        Product prd = new Product();
        prd.setName("My fantastic product");
        Long prdID = productRepository.save(prd).getId();

        // Create the multipart file
        File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("sample-files/sarif-v2.sarif").toURI());
        FileInputStream fis = new FileInputStream(testFile);
        MockMultipartFile multipartFile = new MockMultipartFile("file",fis);

        // Upload the file to create a new source
        Source s = uploadSourceService.uploadFileAndParse(multipartFile, sourceLabel, sourceTool,prdID,false);
        assertNotNull(s);

        Assertions.assertEquals(119,s.getFindings().size());
        Finding firstFinding = s.getFindings().stream().findFirst().get();
        Assertions.assertEquals(true,firstFinding.isValid());
        Assertions.assertEquals(SourceTool.DOCKER_SCOUT, s.getTool());
        Assertions.assertEquals("0.24.1",s.getToolVersion());
    }

}
