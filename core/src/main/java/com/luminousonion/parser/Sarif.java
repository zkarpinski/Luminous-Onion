package com.luminousonion.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminousonion.model.Source;
import org.springframework.web.multipart.MultipartFile;


public class Sarif implements Parser {

    private Sarif() {
        throw new IllegalStateException("Utility class");
    }

    public static Source parse(MultipartFile mpf) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode n = objectMapper.readTree(mpf.getInputStream());
            BaseSarif sarif = objectMapper.readValue(n.toString(), new TypeReference<>() {});

            Source s = SarifV2.parseNode(n, objectMapper);
            // TODO Create logic based on sarif version
            return s;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }


    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class BaseSarif {
        @JsonProperty("version")
        String version;
        @JsonProperty("$schema")
        String schema;
    }

}
