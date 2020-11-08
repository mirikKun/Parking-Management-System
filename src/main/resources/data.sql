INSERT INTO payments (creation_date, amount, status, type)
VALUES ('2020-11-07', 1300, 'Paid', 'Cash'),
       ('2020-10-06', 753, 'Not paid', 'Card'),
       ('2020-10-29', 1550, 'Paid', 'Cash');

INSERT INTO tickets (creation_date, payment_id)
VALUES ('2019-07-13', 1),
       ('2020-10-06', 2),
       ('2020-01-20', 3);

INSERT INTO parkings (address)
VALUES ('Peremohy Ave, 37, Kyiv, 03056'),
       ('Shevchenka St, Dnipro, Dnipropetrovsk Oblast, 49000');

INSERT INTO floors (floor_number, spot_number, parking_id)
VALUES (1, 40, 1),
       (2, 12, 1),
       (1, 35, 2);

INSERT INTO spots (is_free, type, floor_id)
VALUES (true, 'Compact', 1),
       (false, 'Handicapped', 1),
       (true, 'Large', 1),
       (true, 'Electric', 2),
       (false, 'Motorbike', 3);

INSERT INTO accounts (username, password)
VALUES ('username', 'password'),
       ('Qwerty', '123456'),
       ('Andrey', '1111');

INSERT INTO admins (name, address, email, phone, account_id)
VALUES ('Bob Marley', 'Khreschatyk St, 14, Kyiv, 01001', 'marley@gmail.com', '+380505050505', 1),
       ('Bob Dylan', 'Esplanadna St, 17, Kyiv, 02000', 'dylan@gmail.com', '+380665039348', 2),
       ('Andrey Cartman', '1 Naberezhno Khreshchatytska Str, Kyiv, 04070','cartman@gmail.com', '+380999932131', 3);

INSERT INTO vehicles (type)
VALUES ('Car'),
       ('Car'),
       ('Truck'),
       ('Electric'),
       ('Motorbike');