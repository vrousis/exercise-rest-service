package com.rest.exercise.web.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.rest.exercise.model.Item;
import com.rest.exercise.web.ExerciseRestControllerTest;

public class RetrieveItemsForCustomerTest extends ExerciseRestControllerTest {

    @Test
    public void retrieveItemTestSuccessfull()
            throws Exception {
        findItemsMock(Arrays.asList(item, buildItem("2")));
        mvc.perform(get("/rest/item")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_TOKEN,"1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].itemId", is(item.getItemId())));

    }

    @Test
    public void retrieveItemTestUnauthorized()
            throws Exception {
        findItemsMock(Arrays.asList(item, buildItem("2")));
        mvc.perform(get("/rest/item/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void retrieveItemTestEmptyResponse()
            throws Exception {
        findItemsMock(null);
        mvc.perform(get("/rest/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(X_TOKEN,"1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(nullValue())));

    }
}
