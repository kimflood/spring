package com.sample.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.demo.service.TestService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/hello")
    public String index() {
        testService.test();
        return "Greetings from Spring Boot!";
    }
}
