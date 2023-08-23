package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SourceController {
    @Autowired
    SourceRepository sourceRepository;

    @GetMapping("/api/sources")
    public ResponseEntity<List<Source>> getSources() {
        return ResponseEntity.ok(sourceRepository.findAll());
    }

}
