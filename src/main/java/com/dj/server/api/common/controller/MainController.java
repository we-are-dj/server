package com.dj.server.api.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String redirectSwagger() {
        return "redirect:/api/swagger-ui.html";
    }

}
