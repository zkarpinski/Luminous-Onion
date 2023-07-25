package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FindingController {
    @Autowired
    FindingRepository findingRepository;

    @CrossOrigin
    @GetMapping("/api/findings")
    public ResponseEntity<List<Finding>> getFindings() {
        return ResponseEntity.ok(findingRepository.findAll());
    }
}
