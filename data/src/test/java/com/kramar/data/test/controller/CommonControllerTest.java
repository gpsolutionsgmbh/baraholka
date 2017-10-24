package com.kramar.data.test.controller;

import com.kramar.data.DataApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataApplication.class)
@WebAppConfiguration
public class CommonControllerTest {

    @Autowired
    private WebApplicationContext webapp;

    private MockMvc restMvc;

    @Before
    public void setup() {
        restMvc = MockMvcBuilders.webAppContextSetup(webapp).build();
    }

    @Test
    public void fakeRandomEndpointTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(post("/abrakadabra")).andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

}
