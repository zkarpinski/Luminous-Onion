package com.zacharykarpinski.luminousonion.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/trivy/json")
    public ResponseEntity<String> uploadTrivyJsonFile(@RequestParam("file")MultipartFile f) throws JsonProcessingException {
        // TODO: Read the trivy jsvon.
        ObjectMapper objM = new ObjectMapper();

        return ResponseEntity.ok("Parsed");
    }
}
