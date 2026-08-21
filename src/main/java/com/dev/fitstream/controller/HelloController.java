package com.dev.fitstream.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

}
