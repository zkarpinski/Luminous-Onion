package com.zacharykarpinski.luminousonion.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    private static final String API_MESSAGE_KEY = "message";
    private static final String API_STATUS_KEY = "status";

    private ResponseHandler() {
        // Added per Sonar recommendation
        throw new IllegalStateException("Utility class");
    }

    public static ResponseEntity<Object> created(String msg, Object id) {
        Map<String, Object> map = new HashMap<>();
        map.put(API_MESSAGE_KEY, msg);
        map.put(API_STATUS_KEY, HttpStatus.CREATED);
        map.put("id",id);
        return new ResponseEntity<>(map,HttpStatus.CREATED);

    }

    public static ResponseEntity<Object> deleted() {
        return new ResponseEntity<>("",HttpStatus.NO_CONTENT);

    }

    public static ResponseEntity<Object> simple(String msg, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put(API_MESSAGE_KEY, msg);
        map.put(API_STATUS_KEY, status.value());
        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<Object> resp(String msg, HttpStatus status, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put(API_MESSAGE_KEY, msg);
        map.put(API_STATUS_KEY, status.value());
        if (obj != null) {
            map.put("data", obj);
        }
        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<Object> resp(String msg, HttpStatus status, String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(API_MESSAGE_KEY, msg);
        map.put(API_STATUS_KEY, status.value());
        map.put(key,value);
        return new ResponseEntity<>(map,status);
    }
}
