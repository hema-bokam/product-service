CREATE TABLE categories
(
    id   BINARY(16)   NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE order_products
(
    order_id   BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL
);

CREATE TABLE orders
(
    id BINARY(16) NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE products
(
    id            BINARY(16)   NOT NULL,
    title         VARCHAR(255) NOT NULL,
    price         DOUBLE       NOT NULL,
    category_id   BINARY(16)   NOT NULL,
    `description` VARCHAR(255) NULL,
    image         VARCHAR(255) NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE order_products
    ADD CONSTRAINT fk_ordpro_on_order FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_products
    ADD CONSTRAINT fk_ordpro_on_product FOREIGN KEY (product_id) REFERENCES products (id);