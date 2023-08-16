package com.zacharykarpinski.luminousonion;

import com.zacharykarpinski.luminousonion.controller.UploadController;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class FileUploadIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UploadController uploadController;

    @Test
    void givenTrivyFile_whenUploaded_thenParseFile() throws Exception {
       File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("sample-files/trivy-v2.json").toURI());
        FileInputStream fis = new FileInputStream(testFile);
        MockMultipartFile multipartFile = new MockMultipartFile("file",fis);

        // Test valid file - OK
        mockMvc.perform(multipart("/upload")
                        .file(multipartFile)
                        .param("productId","1")
                        .param("sourceTool", SourceTool.AQUA_TRIVY.name()))
                .andExpect(status().isOk());

        // Test missing file = 400 Error
        mockMvc.perform(post("/upload")
                        .content("")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void givenGrypeFile_whenUploaded_thenParseFile() throws Exception {
        File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("sample-files/grype-v0.json").toURI());
        FileInputStream fis = new FileInputStream(testFile);
        MockMultipartFile multipartFile = new MockMultipartFile("file",fis);


        // Test valid file - OK
        mockMvc.perform(multipart("/upload")
                        .file(multipartFile)
                        .param("productId","1")
                        .param("sourceTool", SourceTool.ANCORE_GRYPE.name()))
                .andExpect(status().isOk());

    }

}
