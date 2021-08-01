package com.dj.server.api.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping(value = {"/test-cors"})
    @ResponseStatus(HttpStatus.OK)
    public String testCors() {
        System.out.println("----------- testCors 실행 ----------");
        return "cors passed!";
    }
}
