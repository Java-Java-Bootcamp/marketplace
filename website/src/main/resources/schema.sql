CREATE SCHEMA IF NOT EXISTS marketplace;

drop table if exists marketplace.product cascade;
drop table if exists marketplace.store cascade;
drop table if exists marketplace.product_offer cascade;

CREATE TABLE IF NOT EXISTS marketplace.product (
      id SERIAL PRIMARY KEY,
      name varchar(255) NOT NULL,
      category varchar(255) NOT NULL,
      model varchar(255) NOT NULL,
      manufacturer varchar(255) NOT NULL,
      description TEXT NOT NULL,
      price integer NOT NULL,
      rating integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace.store (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    rating integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace.product_offer (
    id SERIAL PRIMARY KEY,
    product integer NOT NULL REFERENCES marketplace.product (id),
    store integer NOT NULL REFERENCES marketplace.store (id),
    quantity integer NOT NULL
);

