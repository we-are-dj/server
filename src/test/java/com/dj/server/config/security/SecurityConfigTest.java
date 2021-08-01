package com.dj.server.config.security;

import com.dj.server.WeAreDjApplication;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = WeAreDjApplication.class)
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext wac;
    public MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .dispatchOptions(true);
        this.mockMvc = builder.build();
    }

    @Test
    @DisplayName("cors post test")
    public void testCorsPost() throws Exception {
        this.mockMvc
                .perform(post("/test-cors/post")
                        .header("Access-Control-Request-Method", "POST")
                        .header("Origin", "*"))
                .andDo(print())
                .andExpect(content().string("cors post passed!"));
    }

    @Test
    @DisplayName("cors get test")
    public void testCorsGet() throws Exception {
        this.mockMvc
                .perform(get("/test-cors/get")
                        .header("Access-Control-Request-Method", "GET")
                        .header("Origin", "*"))
                .andDo(print())
                .andExpect(content().string("cors get passed!"));
    }

    @Test
    @DisplayName("cors put test")
    public void testCorsPut() throws Exception {
        this.mockMvc
                .perform(put("/test-cors/put")
                        .header("Access-Control-Request-Method", "PUT")
                        .header("Origin", "*"))
                .andDo(print())
                .andExpect(content().string("cors put passed!"));
    }

    @Test
    @DisplayName("cors delete test")
    public void testCorsDelete() throws Exception {
        this.mockMvc
                .perform(delete("/test-cors/delete")
                        .header("Access-Control-Request-Method", "DELETE")
                        .header("Origin", "*"))
                .andDo(print())
                .andExpect(content().string("cors delete passed!"));
    }
}