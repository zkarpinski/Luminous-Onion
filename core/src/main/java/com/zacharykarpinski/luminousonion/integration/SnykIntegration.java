package com.zacharykarpinski.luminousonion.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SnykIntegration {

    Logger logger = LoggerFactory.getLogger(SnykIntegration.class);

    String responseString;

    public void getRest() {
        String url = "https://api.snyk.io/v1/orgs";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "token " + "" );
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
        responseString = response.toString();

    }

}
