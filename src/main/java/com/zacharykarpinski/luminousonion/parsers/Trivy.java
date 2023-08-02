package com.zacharykarpinski.luminousonion.parsers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.model.external.trivy.Vulnerability;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class Trivy implements Parser {

    public static Pair<Source, List<Finding>> parse(MultipartFile mpf) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode n = objectMapper.readTree(mpf.getInputStream());
                TrivyJsonFile trvy = objectMapper.readValue(n.toString(),
                        new TypeReference<TrivyJsonFile>() {});

                // Create a new source record
                Source s = new Source();
                s.setTool("Trivy");
                s.setTarget(trvy.targetName);
                // Mapp to targetTypes to standard
                String targetType;
                switch (trvy.targetType) {
                    case "container_image": targetType= "image";
                        break;
                    default:
                        targetType = trvy.targetType;
                        break;
                }
                s.setTargetType(targetType);


                List<Finding> vulnerabilityList = new ArrayList<Finding>();

                trvy.results.forEach(r -> r.Vulnerabilities.forEach(v -> {
                    v.setSourceTool("Trivy");
                    vulnerabilityList.add((Finding) v);
                }));

                return new Pair<>(s, vulnerabilityList );

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