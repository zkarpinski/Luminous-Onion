package com.luminousonion.controller;

import com.luminousonion.model.Finding;
import com.luminousonion.response.ResponseHandler;
import com.luminousonion.repository.FindingRepository;
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
    public ResponseEntity<List<Finding>> getFindings(
            @RequestParam(name = "activeOnly",required = false, defaultValue = "true") boolean activeOnly)
    {
        if(activeOnly) {
            return ResponseEntity.ok(findingRepository.findBySource_ArchivedFalse());
        }
        else {
            return ResponseEntity.ok(findingRepository.findAll());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFinding(@PathVariable Long id) {
        if (findingRepository.existsById(id)) {
            return ResponseHandler.resp("test",HttpStatus.OK,findingRepository.findById(id));
        }
        return ResponseHandler.simple("id not found",HttpStatus.NOT_FOUND);
    }
}
