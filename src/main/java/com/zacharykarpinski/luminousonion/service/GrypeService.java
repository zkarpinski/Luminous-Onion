package com.zacharykarpinski.luminousonion.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GrypeService {

    @Autowired
    private FindingRepository findingRepository;

    public Boolean ParseGrypeFile(MultipartFile f) throws IOException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode n = objectMapper.readTree(f.getInputStream());
            GrypeJsonFile grype = objectMapper.readValue(n.toString(),
                    new TypeReference<GrypeJsonFile>() {});


            //List<Finding> vulnerabilityList = new ArrayList<Finding>();

            grype.matches.forEach(m -> {
                System.out.println(m.vulnerability.id);
                System.out.println(m.artifact.name);
                System.out.println(grype.targetName);
            });

            // Save the vulnerabilities
            //findingRepository.saveAll(vulnerabilityList);


        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }


        return true;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GrypeJsonFile {
        @JsonProperty("matches")
        List<Match> matches;

        String timestamp;
        String typeName;
        String targetName;

        // Unpacks the source node
        @JsonProperty("source")
        private void unpackSourceObject(JsonNode source) {
            typeName = source.get("type").asText();
            targetName = source.get("target").get("userInput").asText();
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Match {
        public Vulnerability vulnerability;
        public Artifact artifact;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Vulnerability {
        public String id;
        public String dataSource;
        public String severity;
        public String description;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Artifact {
        public String id;
        public String name;
        public String version;
        @JsonProperty("type")
        public String typeName;

    }


}
