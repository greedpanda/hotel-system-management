CREATE TABLE Users
(
    userName   VARCHAR(180) NOT NULL PRIMARY KEY,
    screenName VARCHAR(180) NOT NULL,
    password   VARCHAR(180) NOT NULL
);

CREATE TABLE Administrator
(
    userName VARCHAR(100) NOT NULL PRIMARY KEY,
    CONSTRAINT admins_users_userName_fk
        FOREIGN KEY(userName) REFERENCES Users (userName)
);

CREATE TABLE Location
(
    floor INT NOT NULL PRIMARY KEY
);

CREATE TABLE RoomType
(
    typeID INT          NOT NULL PRIMARY KEY,
    size   VARCHAR(100) NOT NULL
);

CREATE TABLE Room
(
    roomID INT auto_increment PRIMARY KEY,
    number INT          NOT NULL UNIQUE,
    note   VARCHAR(255) NULL,
    typeID INT          NOT NULL,
    floor  INT          NOT NULL,
    beds   INT          NOT NULL,
    CONSTRAINT rooms_locations_floor_fk
        FOREIGN KEY (floor) REFERENCES Location (floor),
    CONSTRAINT rooms_roomtypes_roomTypeID_fk
        FOREIGN KEY (typeID) REFERENCES RoomType (typeID)
);

CREATE INDEX room_number ON Room (number);

CREATE TABLE ReceptionStaff
(
    userName VARCHAR(100) NOT NULL PRIMARY KEY,
    CONSTRAINT reception_users_userName_fk
        FOREIGN KEY(userName) REFERENCES Users (userName)
);

CREATE TABLE Customer
(
    customerID    INT auto_increment PRIMARY KEY,
    publicID      VARCHAR(255) NOT NULL UNIQUE,
    customerName  varchar(255) NOT NULL,
    address       varchar(255) NOT NULL,
    paymentMethod varchar(255) NOT NULL
);

CREATE TABLE Reservation
(
    reservationID INT auto_increment PRIMARY KEY,
    customerID    INT NOT NULL,
    roomID        INT NOT NULL,
    startDate     DATE NOT NULL,
    endDate       DATE NOT NULL,
    paid          BIT NOT NULL,
    CONSTRAINT reservations_customers_customerID_fk
        FOREIGN KEY (customerID) REFERENCES Customer (customerID)
        ON DELETE CASCADE,
    CONSTRAINT reservation_rooms_roomID_fk
        FOREIGN KEY (roomID) REFERENCES Room (roomID)
        ON DELETE CASCADE
);

CREATE VIEW AdminV AS
SELECT *
FROM Users
         NATURAL JOIN Administrator;

CREATE VIEW RoomV AS
SELECT *
FROM Room
         NATURAL JOIN RoomType
         NATURAL JOIN Location;

CREATE VIEW ReceptionV AS
SELECT *
FROM Users
         NATURAL JOIN ReceptionStaff;

CREATE VIEW BookingV AS
SELECT *
FROM Reservation
         NATURAL JOIN Customer
         NATURAL JOIN Room
         NATURAL JOIN RoomType;