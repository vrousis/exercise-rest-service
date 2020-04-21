CREATE KEYSPACE item_engine
WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': 1 };

CREATE TABLE item (
    customer_id text,
    item_id text,
    value text,
    status text,
    PRIMARY KEY (customer_id, item_id)
) WITH CLUSTERING ORDER BY (item_id ASC);