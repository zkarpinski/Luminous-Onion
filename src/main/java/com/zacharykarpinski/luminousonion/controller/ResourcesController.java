package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.SourceTool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/resources")
public class ResourcesController {

    @GetMapping("/sourcetools")
    public ResponseEntity<List<Map<String,String>>> getSourceTools() {

        // Convert SourceTool enum to a list of Map<Label, Value> or Map<ToString(),Name>
        List<Map<String,String>> sourceToolsMap = Stream.of(SourceTool.values())
                .map(source -> Map.of("label", source.toString(), "value", source.name()))
                .sorted(Comparator.comparing(m -> m.get("label"))) //Sort by human-readable label
                .collect(Collectors.toList());

        return ResponseEntity.ok(sourceToolsMap);
    }

}
