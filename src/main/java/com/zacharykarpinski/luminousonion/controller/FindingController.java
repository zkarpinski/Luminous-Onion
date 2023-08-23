package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/findings")
public class FindingController {
    @Autowired
    FindingRepository findingRepository;

    @GetMapping()
    public ResponseEntity<List<Finding>> getFindings() {
        return ResponseEntity.ok(findingRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFinding(@PathVariable Long id) {
        if (findingRepository.existsById(id)) {
            return ResponseHandler.createResponse("test",HttpStatus.OK,findingRepository.findById(id));
        }
        return ResponseHandler.createResponse("ID not found",HttpStatus.NOT_FOUND);
    }
}
