package com.rest.exercise.web.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.rest.exercise.web.ExerciseRestControllerTest;

public class SetItemForCustomerControllerTest extends ExerciseRestControllerTest {


    @Test
    public void setItemForCustomerTestSuccessfull()
            throws Exception {
        findItemMock(item);
        mvc.perform(post("/rest/item")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_TOKEN,"1")
                .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk());

    }

    @Test
    public void setItemForCustomerTestUnauthorized()
            throws Exception {
        findItemMock(item);
        mvc.perform(post("/rest/item")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }
}
