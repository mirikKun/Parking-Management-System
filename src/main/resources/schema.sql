DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS spots;
DROP TABLE IF EXISTS floors;
DROP TABLE IF EXISTS parkings;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS vehicles;

CREATE TABLE payments
(
  id SERIAL PRIMARY KEY,
  creation_date DATE,
  amount INT,
  status VARCHAR (255),
  type VARCHAR(255)
);

CREATE TABLE tickets
(
  id SERIAL PRIMARY KEY,
  creation_date DATE,
  payment_id INT,
  CONSTRAINT FK_tickets_to_payments FOREIGN KEY (payment_id) REFERENCES payments (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE parkings
(
  id SERIAL PRIMARY KEY,
  address VARCHAR (255)
);

CREATE TABLE floors
(
    id SERIAL PRIMARY KEY,
    floor_number INT,
    spot_number INT,
    parking_id INT,
    CONSTRAINT FK_floors_to_parkings FOREIGN KEY (parking_id) REFERENCES parkings (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE spots
(
    id SERIAL PRIMARY KEY,
    is_free BOOLEAN,
    type VARCHAR(255),
    floor_id INT,
    CONSTRAINT FK_spots_to_floors FOREIGN KEY (floor_id) REFERENCES floors (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE accounts
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE admins
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    account_id INTEGER,
    CONSTRAINT FK_admins_to_accounts FOREIGN KEY (account_id) REFERENCES accounts (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE vehicles
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(255)
);