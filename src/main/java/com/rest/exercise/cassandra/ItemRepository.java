package com.rest.exercise.cassandra;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.exercise.model.Item;

@Repository
public interface ItemRepository extends CassandraRepository<Item, Item.ItemCompoundKey> {

    @Query("select * from item where customer_id = ?0")
    List<Item> findByKeyCustomerId(String customerId);

    @Query("select * from ITEM where item_id = :itemId allow filtering")
    Item findByKeyItemId(@Param("itemId") String itemId);

  }