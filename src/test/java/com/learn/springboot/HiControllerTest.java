package com.learn.springboot;

import com.learn.springboot.web.HiController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HiController.class)

public class HiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void return_hi() throws Exception{
        String hi = "hi";

        mvc.perform(get("/hi")).andExpect(status().isOk()).andExpect(content().string(hi));
    }
}
