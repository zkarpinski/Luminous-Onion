package com.zacharykarpinski.luminousonion.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GrypeService {

    @Autowired
    private FindingRepository findingRepository;

    @Autowired
    private SourceRepository sourceRepository;

    public Boolean ParseGrypeFile(MultipartFile f) throws IOException {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode n = objectMapper.readTree(f.getInputStream());
            GrypeJsonFile grype = objectMapper.readValue(n.toString(), new TypeReference<GrypeJsonFile>() {});

            // Loop through each match and cascade parent attributes down
            /*grype.matches.forEach(m -> {
                //TODO: Cascade parent attributes
            });*/

            // Write array of matches to string then convert to a list of findings
            String parsedGrypeJson = objectMapper.writeValueAsString(grype.matches);
            List<Finding> grypeFindingList =  objectMapper.readValue(parsedGrypeJson, new TypeReference<List<Finding>>() {});

            // Create a new source record
            Source s = new Source();
            s.setTool("Grype");
            s.setTargetType(grype.targetType);
            s.setTarget(grype.targetName);
            s.setToolVersion(grype.toolVersion);
            sourceRepository.save(s);

            //Save the new findings
            findingRepository.saveAll(grypeFindingList);

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
