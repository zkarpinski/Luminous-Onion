package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.SourceTool;
import com.zacharykarpinski.luminousonion.service.GrypeService;
import com.zacharykarpinski.luminousonion.service.TrivyService;
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

    private TrivyService trivyService;
    private GrypeService grypeService;
    private final UploadSourceService uploadSourceService;

    public UploadController(TrivyService trivyService, GrypeService grypeService, UploadSourceService uploadSourceService) {
            this.trivyService = trivyService;
            this.grypeService = grypeService;
            this.uploadSourceService = uploadSourceService;
    }

    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Source> uploadFile(
            @RequestParam("file")MultipartFile mpf,
            @RequestParam("productId") Long productId,
            @RequestParam("sourceTool")SourceTool sourceTool) throws IOException {
        try {
            Source newSource = uploadSourceService.uploadFileAndParse(mpf,sourceTool);
            return ResponseEntity.ok(newSource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/trivy/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadTrivyJsonFile(@RequestParam("file")MultipartFile f, @RequestParam("projectId") Long projectId) throws IOException {
        try {
            trivyService.ParseTrivyFile(f);
            return ResponseEntity.ok("Parsed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value="/grype/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Source> uploadGrypeJsonFile(@RequestParam("file")MultipartFile f, @RequestParam("projectId") Long projectId) throws IOException {
        try {

            Source newSource = grypeService.parseGrypeFile(f);
            return ResponseEntity.ok(newSource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
