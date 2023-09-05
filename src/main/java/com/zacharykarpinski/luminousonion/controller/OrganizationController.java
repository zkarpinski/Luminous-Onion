package com.zacharykarpinski.luminousonion.controller;

import com.zacharykarpinski.luminousonion.model.Organization;
import com.zacharykarpinski.luminousonion.model.shared.FindingSeverity;
import com.zacharykarpinski.luminousonion.repository.OrganizationRepository;
import com.zacharykarpinski.luminousonion.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/api/orgs")
public class OrganizationController {
    @Autowired
    OrganizationRepository orgRepository;

    @GetMapping()
    public ResponseEntity<List<Organization>> getOrgs() {
        return ResponseEntity.ok(orgRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Organization> newOrganization(@RequestBody Organization org) {
        return new ResponseEntity<>(orgRepository.save(org), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/findings/summary")
    public ResponseEntity<Object> getFindingSummary(@PathVariable Long id) {
        List<List<Object>> summary = orgRepository.getOrgFindingsCountSummary(id);
        Map<String, Integer> map = summary.stream()
                .collect(toMap(i -> i.get(0).toString(), i -> (int) i.get(1) ));

        // Populate the missing finding severity values
        for (FindingSeverity f: FindingSeverity.values()) {
            map.putIfAbsent(f.name(), 0);
        }

        return ResponseHandler.createResponse("Ok", HttpStatus.OK,map);
    }

}