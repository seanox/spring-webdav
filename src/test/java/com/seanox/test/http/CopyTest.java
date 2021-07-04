package com.seanox.test.http;

import com.seanox.api.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class CopyTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testRequest() {

        //final RequestBuilder requestBuilder = new RequestBuilder().
        //mvc.perform(MockMvcRequestBuilders.c)



    }
}