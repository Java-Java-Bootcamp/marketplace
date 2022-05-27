CREATE SCHEMA IF NOT EXISTS marketplace;

drop table if exists marketplace.product cascade;
drop table if exists marketplace.store cascade;
drop table if exists marketplace.product_offer cascade;
drop table if exists marketplace.customer cascade;
drop table if exists marketplace."order" cascade;
drop table if exists marketplace.order_item cascade;
drop table if exists marketplace.cart_item cascade;

CREATE TABLE IF NOT EXISTS marketplace.product (
      id SERIAL PRIMARY KEY,
      "name" varchar(255) NOT NULL,
      "category" varchar(255) NOT NULL,
      model varchar(255) NOT NULL,
      manufacturer varchar(255) NOT NULL,
      description TEXT NOT NULL,
      price integer NOT NULL,
      rating integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace.store (
    id SERIAL PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    rating integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace.product_offer (
    id SERIAL PRIMARY KEY,
    product integer NOT NULL REFERENCES marketplace.product (id),
    store integer NOT NULL REFERENCES marketplace.store (id),
    quantity integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace.customer (
    id integer PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    address varchar(255) NOT NULL,
    stage varchar(255),
    search_query varchar(255),
    sorting_type_field varchar(255),
    sorting_type_asc_desc varchar(255),
    "offset" integer,
    "limit" integer,
    current_product_id integer
);

CREATE TABLE IF NOT EXISTS marketplace.cart_item (
    id SERIAL PRIMARY KEY,
    customer integer NOT NULL REFERENCES  marketplace.customer (id),
    product_offer integer NOT NULL REFERENCES marketplace.product_offer (id),
    quantity integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace."order" (
    id SERIAL PRIMARY KEY,
    customer integer NOT NULL REFERENCES marketplace.customer (id),
    created_on timestamp DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS marketplace.order_item (
    id SERIAL PRIMARY KEY,
    "order" integer NOT NULL REFERENCES marketplace."order" (id),
    product_offer integer NOT NULL REFERENCES marketplace.product_offer (id),
    quantity integer NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS marketplace.product_id_seq MINVALUE 1 OWNED BY marketplace.product.id;
CREATE SEQUENCE IF NOT EXISTS marketplace.product_offer_id_seq MINVALUE 1 OWNED BY marketplace.product_offer.id;
CREATE SEQUENCE IF NOT EXISTS marketplace.store_id_seq MINVALUE 1 OWNED BY marketplace.store.id;
CREATE SEQUENCE IF NOT EXISTS marketplace.cart_item_id_seq MINVALUE 1 OWNED BY marketplace.cart_item.id;
CREATE SEQUENCE IF NOT EXISTS marketplace.order_id_seq MINVALUE 1 OWNED BY marketplace."order".id;
CREATE SEQUENCE IF NOT EXISTS marketplace.order_item_id_seq MINVALUE 1 OWNED BY marketplace.order_item.id;

