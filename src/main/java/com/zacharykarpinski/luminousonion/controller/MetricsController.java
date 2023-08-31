package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import com.zacharykarpinski.luminousonion.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {
    @Autowired
    SourceRepository sourceRepository;

    private final MetricService metricService;

    public MetricsController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping("/api/metrics/{id}")
    public ResponseEntity<String> getMetrics(@PathVariable Long id) {
        return ResponseEntity.ok(metricService.getDashboardMetrics(id));
    }

}
