package com.zacharykarpinski.luminousonion.parsers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class Grype implements Parser {

    public static Pair<Source, List<Finding>> parse(MultipartFile mpf) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode n = objectMapper.readTree(mpf.getInputStream());
            GrypeJsonFile grype = objectMapper.readValue(n.toString(), new TypeReference<>() {
            });

            // Create a new source record
            Source s = new Source();
            s.setTool("Grype");
            s.setTargetType(grype.targetType);
            s.setTarget(grype.targetName);
            s.setToolVersion(grype.toolVersion);

            // Loop through each match and cascade parent attributes down
            //TODO: Cascade parent attributes
            //grype.matches.forEach(m -> {

            //});

            // Write array of matches to string then convert to a list of findings
            String parsedGrypeJson = objectMapper.writeValueAsString(grype.matches);
            List<Finding> grypeFindingList =  objectMapper.readValue(parsedGrypeJson, new TypeReference<List<Finding>>() {});

            return new Pair<>(s, grypeFindingList);

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
        public String purl;

        public String findingIdentifier;
        public String severity;
        public String primaryUrl;


        final public String sourceTool = "Grype";


        // Unpack vulnerability nested node
        @JsonProperty("vulnerability")
        private void unpackVulnerabilityObject(JsonNode vul) {
            findingIdentifier = vul.get("id").asText();
            severity = vul.get("severity").asText();
            description = vul.path("description").asText();
            primaryUrl = vul.path("dataSource").asText();
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
