USE NitrogenHotel;

INSERT INTO Users (userName, screenName, password)
VALUES ('ha223cz', 'sam', '123'),
       ('mg223uk', 'Mario', '456'),
       ('ah225ex', 'Albert', '111'),
       ('al225nn', 'Alija', '222'),
       ('oz222aw', 'Olga', '789'),
       ('ru222gu', 'Richard', '010'),
       ('gk222hi', 'Georgios', '101'),
       ('pm222py', 'Paolo', '098'),
       ('admin', 'Admin', '000'),
       ('rec', 'Reception', '000');

INSERT INTO Administrator (userName)
VALUES ('ha223cz'),
       ('oz222aw'),
       ('pm222py'),
       ('admin');

INSERT INTO ReceptionStaff (userName)
VALUES ('ru222gu'),
       ('gk222hi');

INSERT INTO Location (floor)
VALUES (1),
       (2),
       (3);

INSERT INTO RoomType (TypeID, SIZE)
VALUES (-1848936376, 'SINGLE'),
       (2022338513, 'DOUBLE'),
       (-1812135842, 'TRIPLE'),
       (2496839, 'QUAD');

INSERT INTO Room (NUMBER, note, typeID, floor, beds)
VALUES (100, 'nice designed room', 2022338513, 1, 2),
       (101, NULL, -1848936376, 1, 1),
       (201, 'has a nice view', -1812135842, 2, 3);

INSERT INTO Customer (publicID, customerName, address, paymentMethod)
VALUES ('ARG 123456', 'Homer SIMPson', '742 Evergreen Terrace', 'Visa'),
       ('19440606-1998', 'Revolver Ocelot', '601 Outer Haven, Bering Sea', 'Master Card');

INSERT INTO Reservation (customerID, roomID, startDate, endDate, paid)
VALUES (1, 1, '2021-4-30', '2021-5-3', 1),
       (2, 2, '2021-5-3', '2021-5-10', 0 ),
       (2, 2, '2021-7-12', '2021-8-15', 1);