package com.zacharykarpinski.luminousonion.parsers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.SourceTool;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class Grype implements Parser {

    private Grype() {
        throw new IllegalStateException("Utility class");
    }

    public static Source parse(MultipartFile mpf) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode n = objectMapper.readTree(mpf.getInputStream());
            GrypeJsonFile grype = objectMapper.readValue(n.toString(), new TypeReference<>() {
            });

            // Loop through each match and cascade parent attributes down
            //TODO: Cascade parent attributes
            //grype.matches.forEach(m -> {
            //});

            // Write array of matches to string then convert to a list of findings
            String parsedGrypeJson = objectMapper.writeValueAsString(grype.matches);
            Set<Finding> findingList =  objectMapper.readValue(parsedGrypeJson, new TypeReference<Set<Finding>>() {});

            // Create a new source record with the new findings
            Source source = new Source();
            source.setTool(SourceTool.ANCORE_GRYPE);
            source.setTargetType(grype.targetType);
            source.setTarget(grype.targetName);
            source.setToolVersion(grype.toolVersion);
            source.setFindings(findingList);

            return source;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }


    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GrypeJsonFile {
        @JsonProperty("matches")
        List<Match> matches;
        String timestamp;
        String targetType;
        String targetName;
        String toolVersion;

        // Unpacks the source node
        @JsonProperty("source")
        private void unpackSourceObject(JsonNode source) {
            targetType = source.get("type").asText();
            targetName = source.get("target").get("userInput").asText();
        }

        // Unpacks the descriptor node
        @JsonProperty("descriptor")
        private void unpackDescriptorObject(JsonNode n) {
            toolVersion = n.path("version").asText();
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Match {
        // Members **should** match the Finding model
        public String title;
        public String description;

        public String packageName;
        public String packagePath;
        public String packageVersionFound;
        public String packageVersionFixed;
        public String purl;

        public String findingIdentifier;
        public String severity;
        public String primaryUrl;

        // Unpack vulnerability nested node
        @JsonProperty("vulnerability")
        private void unpackVulnerabilityObject(JsonNode vul) {
            findingIdentifier = vul.get("id").asText();
            severity = vul.get("severity").asText();
            description = vul.path("description").asText();
            primaryUrl = vul.path("dataSource").asText();
            packageVersionFixed = vul.path("fix").path("versions").path(0).asText();
        }

        // Unpack artifact nested node
        @JsonProperty("artifact")
        private void unpackArtifactObject(JsonNode node) {
            packagePath = node.path("metadata").path("virtualPath").asText();
            packageName = node.get("name").asText();
            packageVersionFound = node.get("version").asText();
            purl = node.get("purl").asText();

        }
    }
}
