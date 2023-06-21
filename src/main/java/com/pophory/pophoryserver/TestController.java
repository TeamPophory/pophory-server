package com.pophory.pophoryserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/pophory")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello, Pophory!");
    }
}
