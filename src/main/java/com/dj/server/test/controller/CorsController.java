package com.dj.server.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * cors 테스트 컨트롤러
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@RestController
public class CorsController {

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
