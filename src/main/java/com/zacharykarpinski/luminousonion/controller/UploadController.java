package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.SourceTool;
import com.zacharykarpinski.luminousonion.service.UploadSourceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
    private final UploadSourceService uploadSourceService;

    public UploadController(UploadSourceService uploadSourceService) {
            this.uploadSourceService = uploadSourceService;
    }

    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Source> uploadFile(
            @RequestParam("file")MultipartFile mpf,
            @RequestParam("productId") Long productId,
            @RequestParam("sourceTool")SourceTool sourceTool) {
        try {
            Source newSource = uploadSourceService.uploadFileAndParse(mpf,sourceTool, productId);
            return ResponseEntity.ok(newSource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
