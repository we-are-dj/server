package com.dj.server.api.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @PostMapping(value = {"/test-cors/post"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPost() {
        return "cors post passed!";
    }

    @GetMapping(value = {"/test-cors/get"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsGet() {
        return "cors get passed!";
    }

    @PutMapping(value = {"/test-cors/put"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPut() {
        return "cors put passed!";
    }

    @DeleteMapping(value = {"/test-cors/delete"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsDelete() {
        return "cors delete passed!";
    }
}
