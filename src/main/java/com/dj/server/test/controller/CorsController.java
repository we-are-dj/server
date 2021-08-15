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
@RequestMapping("/test")
@RestController
public class CorsController {

    @PostMapping(value = {"/cors-post"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPost() {
        return "cors post passed!";
    }

    @GetMapping(value = {"/cors-get"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsGet() {
        return "cors get passed!";
    }

    @PutMapping(value = {"/cors-put"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsPut() {
        return "cors put passed!";
    }

    @DeleteMapping(value = {"/cors-delete"})
    @ResponseStatus(HttpStatus.OK)
    public String testCorsDelete() {
        return "cors delete passed!";
    }
}
