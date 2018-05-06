package com.example.webdemo.web;

import com.example.webdemo.WebDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebDemoApplication.class)
@AutoConfigureMockMvc
public class GitRepoControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnValidResponse() throws Exception {

        mockMvc.perform(get("/getRepository/{owner}/{repo}",
                "lukasz-bacic", "java5pozHibernate")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner.login")
                        .value("lukasz-bacic"));
    }
}
