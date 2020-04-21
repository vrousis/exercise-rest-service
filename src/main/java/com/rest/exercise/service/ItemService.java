package com.rest.exercise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.exercise.cassandra.ItemRepository;
import com.rest.exercise.model.Item;


@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * @param customerId
     * @return
     */
    public List<Item> findByCustomerId(String customerId) {
        List<Item> items = itemRepository.findByKeyCustomerId(customerId);
        return items;
    }

    /**
     * @param itemId
     * @return
     */
    public Item findByItemId(String itemId) {
        Item item = itemRepository.findByKeyItemId(itemId);
        return item;
    }

    /**
     * Save item entry to cassandra
     *
     * @param item
     * @return
     */
    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
