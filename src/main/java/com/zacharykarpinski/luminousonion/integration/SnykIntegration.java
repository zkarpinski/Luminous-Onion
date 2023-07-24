package com.zacharykarpinski.luminousonion.integration;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SnykIntegration {

    public void getRest() {
        System.out.println("Snyk");
        String url = "https://api.snyk.io/v1/orgs";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "token " + "" );
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
        for (Object o: response.getBody()) {
            System.out.println(o.toString());
        }

    }

}
