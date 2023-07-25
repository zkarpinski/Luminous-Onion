package com.zacharykarpinski.luminousonion.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.external.trivy.Vulnerability;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrivyService {

    @Autowired
    private FindingRepository findingRepository;

    public Boolean ParseTrivyFile(MultipartFile f) throws IOException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode n = objectMapper.readTree(f.getInputStream());
            TrivyJsonFile trvy = objectMapper.readValue(n.toString(),
                    new TypeReference<TrivyJsonFile>() {});


            List<Finding> vulnerabilityList = new ArrayList<Finding>();

            trvy.results.forEach(r -> r.Vulnerabilities.forEach(v -> {
                v.setSourceTool("Trivy");
                vulnerabilityList.add((Finding) v);
            }));

            // Save the vulnerabilities
            findingRepository.saveAll(vulnerabilityList);


        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }


        return true;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TrivyJsonFile {
        @JsonProperty("ArtifactName")
        String artifactName;
        @JsonProperty("ArtifactType")
        String artifactType;
        @JsonProperty("Results")
        List<Result> results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Result {
        public String Target;
        public List<Vulnerability> Vulnerabilities;

        @JsonProperty("Class")
        public String className;
        @JsonProperty("Type")
        public String typeName;
    }


}
