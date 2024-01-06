CREATE TABLE products (
    id uuid NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    product_number BIGINT NOT NULL,
    name varchar(255) NULL,
    summary varchar(255) NULL,
    description varchar(5000) NULL,
    price BIGINT NULL,
    rating INTEGER NULL,
    categories varchar(5000) NULL
);

CREATE TABLE orders (
    id uuid NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    order_number BIGINT NOT NULL,
    customer_name varchar(255) NULL,
    address varchar(255) NULL,
    phone_number varchar(255) NULL,
    email varchar(255) NULL
);

CREATE TABLE order_item (
    id uuid NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    quantity INTEGER NULL,
    order_id uuid NULL,
    product_id uuid NULL
);

CREATE TABLE file_storage (
    id uuid NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    file_name varchar(255) NULL,
    file_extension varchar(100) NULL,
    media_type varchar(255) NULL,
    file_data bytea NULL,
    product_id uuid
);

CREATE SEQUENCE product_number_sequence
    START 100000
    INCREMENT 1
    MINVALUE 100000
    MAXVALUE 9223372036854775807
    NO CYCLE
    OWNED BY products.product_number;

CREATE SEQUENCE order_number_sequence
    START 2000000
    INCREMENT 1
    MINVALUE 2000000
    MAXVALUE 9223372036854775807
    NO CYCLE
    OWNED BY orders.order_number;

ALTER TABLE products
ADD CONSTRAINT product_primary_key PRIMARY KEY(id);

ALTER TABLE orders
ADD CONSTRAINT order_primary_key PRIMARY KEY(id);

ALTER TABLE order_item
ADD CONSTRAINT order_item_primary_key PRIMARY KEY(id);

ALTER TABLE products
ADD CONSTRAINT product_number_unique UNIQUE(product_number);

ALTER TABLE orders
ADD CONSTRAINT order_number_unique UNIQUE(order_number);

ALTER TABLE products
ALTER COLUMN product_number SET DEFAULT nextval('product_number_sequence');

ALTER TABLE orders
ALTER COLUMN order_number SET DEFAULT nextval('order_number_sequence');

ALTER TABLE order_item
ADD CONSTRAINT products_order_item_foreign_key
FOREIGN KEY(product_id)
REFERENCES products(id);

ALTER TABLE order_item
ADD CONSTRAINT orders_order_item_foreign_key
FOREIGN KEY(order_id)
REFERENCES orders(id);

ALTER TABLE file_storage
ADD CONSTRAINT file_storage_primary_key PRIMARY KEY(id);

ALTER TABLE file_storage
ADD CONSTRAINT product_id_foreign_key
FOREIGN KEY(product_id)
REFERENCES products(id);

