package com.zacharykarpinski.luminousonion.parsers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.shared.FindingSeverity;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.shared.FindingType;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
        public FindingSeverity severity;
        @Enumerated(EnumType.STRING)
        public FindingType findingType;
        public String originalSeverity;
        public String primaryUrl;

        // Custom calculations
        public String getTitle() {
            return "%s:%s | %s".formatted(packageName, packageVersionFound, findingIdentifier);
        }
        public void setSeverity(String severity) {
            this.originalSeverity = severity;
            this.severity = switch (severity.toUpperCase()) {
                case ("CRITICAL") -> FindingSeverity.CRITICAL;
                case ("HIGH") -> FindingSeverity.HIGH;
                case ("MEDIUM") -> FindingSeverity.MEDIUM;
                case ("LOW") -> FindingSeverity.LOW;
                case ("NEGLIGIBLE") -> FindingSeverity.INFORMATIONAL;
                default -> FindingSeverity.INFORMATIONAL;
            };
        }

        // Unpack vulnerability nested node
        @JsonProperty("vulnerability")
        private void unpackVulnerabilityObject(JsonNode vul) {
            findingIdentifier = vul.get("id").asText();
            this.setSeverity(vul.get("severity").asText());
            description = vul.path("description").asText();
            primaryUrl = vul.path("dataSource").asText();
            packageVersionFixed = vul.path("fix").path("versions").path(0).asText();
        }

        // Unpack artifact nested node
        @JsonProperty("artifact")
        private void unpackArtifactObject(JsonNode node) {
            packageName = node.get("name").asText();
            packageVersionFound = node.get("version").asText();
            purl = node.get("purl").asText();

            // Map artifact.type to findingType
            String artifactType = node.path("type").asText();
            findingType = switch (artifactType) {
                case ("java-archive") -> FindingType.PACKAGES;
                case ("deb") -> FindingType.OS;
                case ("binary") -> FindingType.BINARY;
                default -> FindingType.OTHER;
            };

            // When java-archive or other packages, use virtualPath else location.path
            packagePath = findingType == FindingType.PACKAGES ?
                    node.path("metadata").path("virtualPath").asText() :
                    node.path("locations").path(0).path("path").asText();

        }
    }
}
