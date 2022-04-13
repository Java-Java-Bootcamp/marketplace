CREATE SCHEMA IF NOT EXISTS marketplace2;

drop table if exists marketplace2.product cascade;
drop table if exists marketplace2.store cascade;
drop table if exists marketplace2.product_offer cascade;

CREATE TABLE IF NOT EXISTS marketplace2.product (
      id SERIAL PRIMARY KEY,
      name varchar(255) NOT NULL,
      category integer NOT NULL,
      model varchar(255) NOT NULL,
      manufacturer varchar(255) NOT NULL,
      description TEXT NOT NULL,
      price integer NOT NULL,
      rating integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace2.store (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    rating integer NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace2.product_offer (
    id SERIAL PRIMARY KEY,
    product integer NOT NULL REFERENCES marketplace2.product (id),
    store integer NOT NULL REFERENCES marketplace2.store (id),
    quantity integer NOT NULL
);

