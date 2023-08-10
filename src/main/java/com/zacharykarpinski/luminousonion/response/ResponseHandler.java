package com.zacharykarpinski.luminousonion.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    private ResponseHandler() {
        // Added per Sonar recommendation
        throw new IllegalStateException("Utility class");
    }
    public static ResponseEntity<Object> createResponse(String msg, HttpStatus status, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("status", status.value());
        if (obj != null) {
            map.put("data", obj);
        }
        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<Object> createResponse(String msg, HttpStatus status, String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("status", status.value());
        map.put(key,value);
        return new ResponseEntity<>(map,status);
    }
}
