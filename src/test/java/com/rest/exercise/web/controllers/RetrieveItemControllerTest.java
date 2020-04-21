package com.rest.exercise.web.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.rest.exercise.model.Item;
import com.rest.exercise.web.ExerciseRestControllerTest;

public class RetrieveItemControllerTest extends ExerciseRestControllerTest {

    @Test
    public void retrieveItemTestSuccessfull()
            throws Exception {
        findItemMock(item);
        mvc.perform(get("/rest/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_TOKEN,"1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.itemId", is(item.getItemId())));

    }

    @Test
    public void retrieveItemTestUnauthorized()
            throws Exception {
        findItemMock(item);
        mvc.perform(get("/rest/item/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void retrieveItemTestEmptyResponse()
            throws Exception {
        findItemMock(null);
        mvc.perform(get("/rest/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_TOKEN,"1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(nullValue())));

    }

}
