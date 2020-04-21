package com.rest.exercise.model;

import java.io.Serializable;
import java.util.Objects;


import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@Table
@JsonPropertyOrder({ "itemId", "customerId", "value", "status"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {

    public static final String CUSTOMER_ID = "customer_id";
    public static final String ITEM_ID = "item_id";

    @PrimaryKeyClass
    public static class ItemCompoundKey implements Serializable {

        @PrimaryKeyColumn(
                name = CUSTOMER_ID, ordinal = 1, type = PrimaryKeyType.PARTITIONED)
        private String customerId;

        @PrimaryKeyColumn(name = ITEM_ID, ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
        private String itemId;

        public ItemCompoundKey() {
        }

        public ItemCompoundKey(String customerId, String itemId) {
            this.customerId = customerId;
            this.itemId = itemId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getItemId() {
            return itemId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
            result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemCompoundKey that = (ItemCompoundKey) o;
            return Objects.equals(customerId, that.customerId) &&
                    Objects.equals(itemId, that.itemId);
        }

        @Override
        public String toString() {
            return "ItemCompoundKey{" +
                    "customerId='" + customerId + '\'' +
                    ", itemId='" + itemId + '\'' +
                    '}';
        }
    }

    @PrimaryKey
    private ItemCompoundKey key = new ItemCompoundKey();

    private String status;
    private String value;

    private Item() {
    }

    @JsonIgnore
    public ItemCompoundKey getKey() {
        return key;
    }

    public String getCustomerId() {
        return this.key.customerId;
    }

    public String getItemId() {
        return this.key.itemId;
    }

    public String getStatus() {
        return this.status;
    }

    public String getValue() {
        return this.value;
    }

    @JsonSetter("itemId")
    public void setItemId(String itemId) {
        this.key.itemId = itemId;
    }

    @JsonSetter("customerId")
    public void setCustomerId(String customerId) {
        this.key.customerId = customerId;
    }

    @JsonSetter("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonSetter("value")
    public void setValue(String value) {
        this.value = value;
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static class ItemBuilder {
        private Item item;

        public ItemBuilder() {
            item = new Item();
            item.key = new ItemCompoundKey();
        }

        public ItemBuilder withCustomerId(String customerId) {
            item.key.customerId = customerId;
            return this;
        }

        public ItemBuilder withItemId(String itemId) {
            item.key.itemId = itemId;
            return this;
        }

        public ItemBuilder withStatus(String status) {
            item.status = status;
            return this;
        }

        public ItemBuilder withValue(String value) {
            item.value = value;
            return this;
        }


        public Item build() {
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(key, item.key) &&
                Objects.equals(status, item.status) &&
                Objects.equals(value, item.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, status, value);
    }

    @Override
    public String toString() {
        return "Item{" +
                "key=" + key +
                ", status='" + status + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}