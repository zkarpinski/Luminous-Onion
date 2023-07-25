package com.zacharykarpinski.luminousonion.controller;

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

    public UploadController(TrivyService trivyService) {this.trivyService = trivyService;}

    @PostMapping("/trivy/json")
    public ResponseEntity<String> uploadTrivyJsonFile(@RequestParam("file")MultipartFile f) throws IOException {
        try {


        trivyService.ParseTrivyFile(f);

        return ResponseEntity.ok("Parsed");
        } catch (Exception e) {
            return ResponseEntity.ok("Failed");
        }
    }
}
