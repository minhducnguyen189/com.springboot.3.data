CREATE TABLE customers (
    id uuid NOT NULL,
    full_name varchar(255) NULL,
    email varchar(255) NULL,
    address varchar(255) NULL,
    phone varchar(255) NULL,
    gender varchar(10) NULL,
    dob DATE NULL,
    loyalty_card uuid
);

CREATE TABLE loyalty_cards (
    id uuid NOT NULL,
    points INTEGER NULL,
    issue_date DATE NULL
);

ALTER TABLE customers
ADD CONSTRAINT customers_primary_key PRIMARY KEY(id);

ALTER TABLE customers
ADD CONSTRAINT customers_email_unique UNIQUE(email);

ALTER TABLE customers
ADD CONSTRAINT customers_phone_unique UNIQUE(phone);

ALTER TABLE loyalty_cards
ADD CONSTRAINT loyalty_cards_primary_key PRIMARY KEY(id);

ALTER TABLE customers
ADD CONSTRAINT customers_loyalty_cards_foreign_key
FOREIGN KEY(loyalty_card)
REFERENCES loyalty_cards(id);

CREATE TABLE orders (
    id uuid NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_updated_date TIMESTAMP NOT NULL,
    order_name varchar(255) NULL,
    order_status varchar(100) NULL,
    customer_id uuid
);

ALTER TABLE orders
ADD CONSTRAINT orders_primary_key PRIMARY KEY(id);

ALTER TABLE orders
ADD CONSTRAINT orders_customers_foreign_key
FOREIGN KEY(customer_id)
REFERENCES customers(id);

CREATE TABLE items (
    id uuid NOT NULL,
    item_name varchar(255),
    quantity INTEGER NULL,
    price DOUBLE PRECISION NULL,
    order_id uuid NULL
);

ALTER TABLE items
ADD CONSTRAINT items_primary_key PRIMARY KEY(id);

ALTER TABLE items
ADD CONSTRAINT items_orders_foreign_key
FOREIGN KEY(order_id)
REFERENCES orders(id);

CREATE VIEW customers_view AS
    SELECT
        c.id,
        c.full_name,
        c.email,
        c.address,
        c.phone,
        c.gender,
        c.dob,
        l.points
    FROM
        customers c
    LEFT JOIN
        loyalty_cards l ON c.loyalty_card = l.id;

CREATE VIEW orders_view AS
SELECT
    o.id,
    o.created_date,
    o.last_updated_date,
    o.order_name,
    o.order_status,
    o.customer_id
FROM
    orders o;
