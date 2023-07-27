package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.service.GrypeService;
import com.zacharykarpinski.luminousonion.service.TrivyService;
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

    public UploadController(TrivyService trivyService, GrypeService grypeService) {
            this.trivyService = trivyService;
            this.grypeService = grypeService;
    }

    @PostMapping("/trivy/json")
    public ResponseEntity<String> uploadTrivyJsonFile(@RequestParam("file")MultipartFile f) throws IOException {
        try {
            trivyService.ParseTrivyFile(f);
            return ResponseEntity.ok("Parsed");
        } catch (Exception e) {
            return ResponseEntity.ok("Failed");
        }
    }

    @PostMapping("/grype/json")
    public ResponseEntity<String> uploadGrypeJsonFile(@RequestParam("file")MultipartFile f) throws IOException {
        try {
            grypeService.parseGrypeFile(f);
            return ResponseEntity.ok("Parsed");
        } catch (Exception e) {
            return ResponseEntity.ok("Failed");
        }
    }
}
