package com.zacharykarpinski.luminousonion.parser;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        if (!trvy.results.isEmpty()) {
            trvy.results.forEach(r -> {
                r.vulnerabilities.forEach(v -> {

                });
                try {
                    findingList.addAll(objectMapper.readValue(
                            objectMapper.writeValueAsString(r.vulnerabilities),
                            new TypeReference<Set<Finding>>() {
                            }));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
        }

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
        String targetType = "";
        @JsonProperty("Results")
        List<Result> results = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Result {
        @JsonProperty("Target")
        public String target;
        @JsonProperty("Vulnerabilities")
        public List<Vulnerability> vulnerabilities;

        @JsonProperty("Class")
        public String className;
        @JsonProperty("Type")
        public String typeName;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Vulnerability extends AbstractParserFinding {
        @JsonAlias("Description")
        protected String description;
        @JsonAlias("Title") // We map title to short description
        protected String shortDescription;
        @JsonAlias("PkgName")
        protected String packageName;
        @JsonAlias("PkgPath")
        protected String packagePath;
        @JsonAlias("InstalledVersion")
        protected String packageVersionFound;
        @JsonAlias("FixedVersion")
        protected String packageVersionFixed;
        @JsonAlias("VulnerabilityID")
        protected String findingIdentifier;
        @JsonAlias("PrimaryURL")
        protected String primaryUrl;

        @Override
        @JsonAlias("Severity")
        public void setSeverity(String severity) {
            super.setSeverity(severity);
            // For severity, we accept the default mapping
        }
    }
}
