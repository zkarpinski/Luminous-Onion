package com.luminousonion.parser;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminousonion.model.Finding;
import com.luminousonion.model.Source;
import com.luminousonion.model.shared.FindingSeverity;
import com.luminousonion.model.shared.SourceTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;


public class SarifV2 implements Parser {

    public static Source parse(MultipartFile mpf) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode n = objectMapper.readTree(mpf.getInputStream());
            SarifV2File sarif = objectMapper.readValue(n.toString(), new TypeReference<>() {});

            // Create a new source record with the new findings
            Source source = new Source();
            return source;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static Source parseNode(JsonNode n, ObjectMapper objectMapper) {
        try {
            SarifV2File sarif = objectMapper.readValue(n.toString(), new TypeReference<>() {});

            // Write array of "rules" to string then convert to a list of findings
            List<Rule> ruleList = sarif.runs.get(0).tool.driver.rules.stream().toList();
            String parsedSarifRules = objectMapper.writeValueAsString(ruleList);
            Set<Finding> findingSet = objectMapper.readValue(parsedSarifRules, new TypeReference<>(){});

            // Create a new source record with the new findings
            Source source = new Source();
            source.setTool(SourceTool.DOCKER_SCOUT);
            source.setToolVersion(sarif.runs.get(0).tool.driver.version);
            source.setFindings(findingSet);
            return source;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SarifV2File {
        @JsonProperty("version")
        String version;
        @JsonProperty("$schema")
        String schema;

        @JsonProperty("runs")
        List<Runs> runs;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Runs {
        @JsonProperty("tool")
        Tool tool;

        @JsonProperty("results")
        List<Result> results;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Tool {
        @JsonProperty("driver")
        Driver driver;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Driver {
        @JsonProperty("fullName")
        String fullName;
        @JsonProperty("name")
        String name;

        @JsonProperty("version")
        String version;

        @JsonProperty("rules")
        List<Rule> rules;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Rule {
        // Members **should** match the Finding model
        public String title;
        public String description;

        public String packageName;
        public String packagePath;
        public String packageVersionFound;
        public String packageVersionFixed;
        public String purl;

        @JsonAlias("id")
        @JsonProperty("findingIdentifier")
        String findingIdentifier;
        public FindingSeverity severity;

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
                case ("UNSPECIFIED") -> FindingSeverity.INFORMATIONAL;
                default -> FindingSeverity.INFORMATIONAL;
            };
        }


        // Unpacks the help node
        @JsonProperty("help")
        private void unpackHelpObject(JsonNode node) {
            description = node.path("text").asText();

        }

        // Unpacks the properties node
        @JsonProperty("properties")
        private void unpackSourceObject(JsonNode props) {
            packageVersionFixed = props.get("fixed_version").asText();
            purl = props.get("purl").asText();
            this.setSeverity(props.get("cvssV3_severity").asText());

            //Extract version from purl
            if (purl != null && purl.contains("@")) {
                // Expected texts:
                // pkg:maven/org.yaml/snakeyaml@1.28
                // pkg:deb/ubuntu/shadow@1:4.8.1-1ubuntu5.20.04.4?os_distro=focal&os_name=ubuntu&os_version=20.04

                // Parse packageName and group id
                String leftText = StringUtils.substringBefore(purl,"@");
                packageName = StringUtils.substringAfterLast(leftText,"/");

                // Parse version
                String rightText = StringUtils.substringAfter(purl,"@");
                packageVersionFound = StringUtils.substringBefore(rightText,"?");

            } else {
                packageName = purl;
            }

        }


    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Result {
        String ruleId;
        long ruleIndex;
        String kind;
        String level;
    }

}
