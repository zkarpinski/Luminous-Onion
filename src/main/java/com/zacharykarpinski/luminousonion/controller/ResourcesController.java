package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.SourceTool;
import com.zacharykarpinski.luminousonion.model.status.FindingStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/resources")
public class ResourcesController {
    static final String MAPPING_LABEL_TEXT = "label";
    static final String MAPPING_VALUE_TEXT = "value";

    @GetMapping("/sourcetools")
    public ResponseEntity<List<Map<String,String>>> getSourceTools() {

        // Convert SourceTool enum to a list of Map<Label, Value> or Map<ToString(),Name>
        List<Map<String,String>> sourceToolsMap = Stream.of(SourceTool.values())
                .map(source -> Map.of(MAPPING_LABEL_TEXT, source.toString(),
                        MAPPING_VALUE_TEXT, source.name()))
                .sorted(Comparator.comparing(m -> m.get(MAPPING_LABEL_TEXT))) //Sort by human-readable label
                .toList();

        return ResponseEntity.ok(sourceToolsMap);
    }

    @GetMapping("/findingstatuses")
    public ResponseEntity<List<Map<String,String>>> getFindingStatuses() {

        // Convert SourceTool enum to a list of Map<Label, Value> or Map<ToString(),Name>
        List<Map<String,String>> statusMap = Stream.of(FindingStatus.values())
                .map(status -> Map.of(MAPPING_LABEL_TEXT, status.toString(),
                        MAPPING_VALUE_TEXT, status.name()))
                .sorted(Comparator.comparing(m -> m.get(MAPPING_LABEL_TEXT))) //Sort by human-readable label
                .toList();

        return ResponseEntity.ok(statusMap);
    }

}
