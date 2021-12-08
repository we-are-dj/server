package com.dj.server.test.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/test")
@RestController
public class CookieTest {

    @GetMapping("/cookie-test")
    public String test(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("sameSiteCookie", "sameSiteCookieValue")
                .domain("http://localhost:3000/")
                .sameSite("Nonew")
                .secure(true)
                .path("/")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return "test";
    }

}
