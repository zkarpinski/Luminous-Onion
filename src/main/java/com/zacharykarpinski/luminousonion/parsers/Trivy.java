package com.zacharykarpinski.luminousonion.parsers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import com.zacharykarpinski.luminousonion.model.external.trivy.Vulnerability;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trivy implements Parser {
    private Trivy() {
        throw new IllegalStateException("Utility class");
    }

    public static Source parse(MultipartFile mpf) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode n = objectMapper.readTree(mpf.getInputStream());
                TrivyJsonFile trvy = objectMapper.readValue(n.toString(),
                        new TypeReference<TrivyJsonFile>() {});


                Set<Finding> findingList = new HashSet<>();
                trvy.results.forEach(r -> r.Vulnerabilities.forEach(v -> {
                    findingList.add((Finding) v);
                }));

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

            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
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

    }