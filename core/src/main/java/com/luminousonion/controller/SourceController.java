package com.luminousonion.controller;

import com.luminousonion.model.Source;
import com.luminousonion.response.ResponseHandler;
import com.luminousonion.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sources")
public class SourceController {
    @Autowired
    SourceRepository sourceRepository;

    @GetMapping()
    public ResponseEntity<List<Source>> getSources() {
        return ResponseEntity.ok(sourceRepository.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteSource(@PathVariable Long id) {
        if (sourceRepository.existsById(id)) {
            sourceRepository.deleteById(id);
            return ResponseHandler.deleted();
        }
        return ResponseHandler.simple("id not found", HttpStatus.NOT_FOUND);
    }

}
