package com.dj.server.config.security;

import com.dj.server.WeAreDjApplication;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = WeAreDjApplication.class)
public class SecurityConfigTest {
    @Autowired
    private WebApplicationContext wac;
    public MockMvc mockMvc;
    @Before("testCors")
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .dispatchOptions(true);
        this.mockMvc = builder.build();
    }
    @Test
    @DisplayName("cors test")
    public void testCors() throws Exception {
        this.mockMvc
                .perform(options("/test-cors")
                        .header("Access-Control-Request-Method", "GET")
                        .header("Origin", "localhost:8080/"))
                .andDo(print())
                .andExpect(content().string("cors passed!"));
    }
    @SpringBootApplication(scanBasePackages = {"com.dj.server"})
    @Controller
    static class TestApplication {
        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }
        @GetMapping(value = {"test-cors"})
        @ResponseStatus(HttpStatus.OK)
        public @ResponseBody
        String testCors() {
            return "cors passed!";
        }
    }
}