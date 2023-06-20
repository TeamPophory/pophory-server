package com.pophory.pophoryserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/pophory")
    public String test() {
        return "Hello, Pophory!";
    }
}
