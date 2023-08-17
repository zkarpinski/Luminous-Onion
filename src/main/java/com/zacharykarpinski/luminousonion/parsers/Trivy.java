package com.zacharykarpinski.luminousonion.parsers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.shared.FindingSeverity;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Trivy input structure
 * {
 *     ...
 *     "Metadata" : {...},
 *     "Results" : {
 *         {
 *             "Target":"example/docker",
 *             ...
 *             "Vulnerabilities": [{...},{...},{...}]
 *         },
 *         {
 *             "Target":"java",
 *             ...
 *             "Vulnerabilities": [{...},{...},{...}]
 *         }
 *     }
 * }
 */

public class Trivy implements Parser {
    private Trivy() {
        throw new IllegalStateException("Utility class");
    }

    public static Source parse(MultipartFile mpf) {
        ObjectMapper objectMapper = new ObjectMapper();
        TrivyJsonFile trvy;
        try {
            JsonNode n = objectMapper.readTree(mpf.getInputStream());
             trvy = objectMapper.readValue(n.toString(),
                    new TypeReference<TrivyJsonFile>() {
                    });

        }
        catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        Set<Finding> findingList = new HashSet<>();

        trvy.results.forEach(r -> {
            r.Vulnerabilities.forEach(v -> {

            });
            try {
                findingList.addAll(objectMapper.readValue(
                        objectMapper.writeValueAsString(r.Vulnerabilities),
                        new TypeReference<Set<Finding>>() {}));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        });

            // Create a new source record
            Source source = new Source();
            source.setTool(SourceTool.AQUA_TRIVY);
            source.setTarget(trvy.targetName);
            source.setFindings(findingList);
            // Map to targetTypes to standard
            String targetType = switch (trvy.targetType) {
                case "container_image" -> "image";
                default -> trvy.targetType;
            };
            source.setTargetType(targetType);

            return source;


    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TrivyJsonFile {
        @JsonProperty("ArtifactName")
        String targetName;
        @JsonProperty("ArtifactType")
        String targetType;
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Vulnerability {
        public String title; // No mapping on purpose
        @JsonAlias("Description")
        public String description;
        @JsonAlias("Title") // We map title to short description
        public String shortDescription;
        @JsonAlias("PkgName")
        public String packageName;
        @JsonAlias("PkgPath")
        public String packagePath;
        @JsonAlias("InstalledVersion")
        public String packageVersionFound;
        @JsonAlias("FixedVersion")
        public String packageVersionFixed;

        @JsonAlias("VulnerabilityID")
        public String findingIdentifier;

        @Enumerated(EnumType.STRING)
        public FindingSeverity severity;
        public String originalSeverity;
        @JsonAlias("PrimaryURL")
        public String primaryUrl;

        // Custom calculations
        public String getTitle() {
            return "%s:%s | %s".formatted(packageName, packageVersionFound, findingIdentifier);
        }


        @JsonAlias("Severity")
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
    }
}