package com.learn.springboot.web;

import com.learn.springboot.config.auth.SecurityConfig;
import com.learn.springboot.web.HiController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HiController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE
            , classes = SecurityConfig.class)})
public class HiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void return_hi() throws Exception{
        String hi = "hi";

        mvc.perform(get("/hi")).andExpect(status().isOk()).andExpect(content().string(hi));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void return_hiDto() throws Exception{
        String name = "hi";
        int amount = 1349;

        mvc.perform(get("/hi/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
