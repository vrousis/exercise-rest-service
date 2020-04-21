package com.rest.exercise.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.exercise.model.Item;
import com.rest.exercise.service.ItemService;
import com.rest.exercise.web.controllers.ExerciseRestController;

@RunWith(SpringRunner.class)
@WebMvcTest(ExerciseRestController.class)
public abstract class ExerciseRestControllerTest {

    protected static final String X_TOKEN = "X-TOKEN";

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected ItemService itemService;

    @MockBean
    protected RequestHeaders requestHeaders;

    protected ObjectMapper objectMapper;
    protected Item item;

    @Before
    public void setup(){
        item = buildItem();
        objectMapper = new ObjectMapper();
    }

    protected Item buildItem() {
        return buildItem("1");
    }

    protected Item buildItem(String itemId) {
        return new Item.ItemBuilder()
                .withItemId(itemId)
                .withCustomerId("1")
                .withStatus("A")
                .withValue("20")
                .build();
    }

    protected void findItemMock(Item item) {
        given(itemService.findByItemId(any())).willReturn(item);
    }

    protected void findItemsMock(List<Item> items) {
        given(itemService.findByCustomerId(any())).willReturn(items);
    }

    protected void saveItemMock(Item item) {
        given(itemService.save(any())).willReturn(item);
    }
}
