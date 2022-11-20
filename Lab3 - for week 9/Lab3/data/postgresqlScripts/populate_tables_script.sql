INSERT INTO Person(personid, firstname, lastname, dateofbirth) VALUES(1, 'Max', 'Ionescu', '1997-05-12');
INSERT INTO Person(personid, firstname, lastname, dateofbirth) VALUES(2, 'Ionela', 'Pop', '1987-06-13');
INSERT INTO Person(personid, firstname, lastname, dateofbirth) VALUES(3, 'Mariuca', 'Popescu', '1962-01-09');
INSERT INTO Person(personid, firstname, lastname, dateofbirth) VALUES(4, 'Alex', 'Pop', '2007-10-26');
INSERT INTO Person(personid, firstname, lastname, dateofbirth) VALUES(5, 'Marian', 'Pop', '1990-04-09');
INSERT INTO Person(personid, firstname, lastname, dateofbirth) VALUES(6, 'Maria', 'Ionescu', '1994-07-28');
INSERT INTO Person(personid, firstname, lastname, dateofbirth) VALUES(7, 'Marian', 'Ionescu', '1977-02-24');

INSERT INTO Driver(driverid, monthsactive) VALUES(1, 18);
INSERT INTO Driver(driverid, monthsactive) VALUES(5, 4);
INSERT INTO Driver(driverid, monthsactive) VALUES(7, 15);

INSERT INTO Passenger(passengerid, timestravelled) VALUES(2, 20);
INSERT INTO Passenger(passengerid, timestravelled) VALUES(3, 5);
INSERT INTO Passenger(passengerid, timestravelled) VALUES(4, 60);
INSERT INTO Passenger(passengerid, timestravelled) VALUES(6, 120);

INSERT INTO BusCompany(companyid, name) VALUES(1, 'Solaris');
INSERT INTO BusCompany(companyid, name) VALUES(2, 'Irisbus');

INSERT INTO Bus(busid, companyid, driverid, modelname) VALUES(1, 1, 1, 'ElectricBus');
INSERT INTO Bus(busid, companyid, driverid, modelname) VALUES(2, 1, 5, 'GT5500');
INSERT INTO Bus(busid, companyid, driverid, modelname) VALUES(3, 1, 7, 'AR800');

INSERT INTO City(cityid, name, population) VALUES(1, 'Cluj-Napoca', 300000);
INSERT INTO City(cityid, name, population) VALUES(2, 'Dej', 34000);
INSERT INTO City(cityid, name, population) VALUES(3, 'Floresti', 22000);
INSERT INTO City(cityid, name, population) VALUES(4, 'Gheorgheni', 18000);

INSERT INTO BusStation(stationid, cityid, name) VALUES(1, 1, 'Minerva');
INSERT INTO BusStation(stationid, cityid, name) VALUES(2, 1, 'Memorandumului');
INSERT INTO BusStation(stationid, cityid, name) VALUES(3, 2, 'Centru');
INSERT INTO BusStation(stationid, cityid, name) VALUES(4, 3, 'Sub Cetate');

INSERT INTO BusStop(stoptime, busid, stationid) VALUES('12:40:00', 1, 1);
INSERT INTO BusStop(stoptime, busid, stationid) VALUES('13:40:00', 1, 2);
INSERT INTO BusStop(stoptime, busid, stationid) VALUES('13:40:00', 2, 2);
INSERT INTO BusStop(stoptime, busid, stationid) VALUES('16:40:00', 3, 3);

INSERT INTO Luggage(luggageid, passengerid, weight) VALUES(1, 2, 5);
INSERT INTO Luggage(luggageid, passengerid, weight) VALUES(2, 3, 10);
INSERT INTO Luggage(luggageid, passengerid, weight) VALUES(3, 4, 25);
INSERT INTO Luggage(luggageid, passengerid, weight) VALUES(4, 2, 15);
INSERT INTO Luggage(luggageid, passengerid, weight) VALUES(5, 2, 10);
INSERT INTO Luggage(luggageid, passengerid, weight) VALUES(6, 3, 10);

INSERT INTO Ticket(ticketid, passengerid, busid, boardingtime, price) VALUES(1, 2, 1, '12:40:00', 20);
INSERT INTO Ticket(ticketid, passengerid, busid, boardingtime, price) VALUES(2, 2, 2, '13:40:00', 15);
INSERT INTO Ticket(ticketid, passengerid, busid, boardingtime, price) VALUES(3, 4, 1, '14:40:00', 30);
INSERT INTO Ticket(ticketid, passengerid, busid, boardingtime, price) VALUES(4, 6, 1, '17:40:00', 5);
INSERT INTO Ticket(ticketid, passengerid, busid, boardingtime, price) VALUES(5, 2, 2, '16:40:00', 25);
INSERT INTO Ticket(ticketid, passengerid, busid, boardingtime, price) VALUES(6, 6, 1, '12:40:00', 30);
INSERT INTO Ticket(ticketid, passengerid, busid, boardingtime, price) VALUES(7, 6, 1, '21:40:00', 25);

INSERT INTO Feedback(passengerid, companyid, review) VALUES(2, 1, 'Expensive');
INSERT INTO Feedback(passengerid, companyid, review) VALUES(2, 2, 'Friendly staff');
INSERT INTO Feedback(passengerid, companyid, review) VALUES(3, 1, 'Pleasant');
INSERT INTO Feedback(passengerid, companyid, review) VALUES(6, 2, 'Very comfortable');