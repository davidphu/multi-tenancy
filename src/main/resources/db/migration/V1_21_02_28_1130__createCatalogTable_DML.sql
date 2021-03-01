CREATE TABLE catalog
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_description VARCHAR(4000),
    sale_price FLOAT NOT NULL,
    available_quantity INT NOT NULL,
    listing_date DATETIME NOT NULL,
    listing_end_date DATETIME
)