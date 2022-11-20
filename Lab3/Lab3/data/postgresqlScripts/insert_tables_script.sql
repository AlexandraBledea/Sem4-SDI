CREATE TABLE Person(
	PersonId bigint PRIMARY KEY,
	FirstName text,
	LastName text,
	DateOfBirth date
);

CREATE TABLE Driver(
	DriverId bigint PRIMARY KEY ,
	MonthsActive int,
	FOREIGN KEY (DriverId) REFERENCES Person(PersonId) ON DELETE CASCADE
);

CREATE TABLE Passenger(
	PassengerId bigint PRIMARY KEY,
	TimesTravelled int,
	FOREIGN KEY (PassengerId) REFERENCES Person(PersonId) ON DELETE CASCADE
);

-- 1:m relationship with Passenger
CREATE TABLE Luggage(
	LuggageId bigint PRIMARY KEY,
	PassengerId bigint,
	FOREIGN KEY (PassengerId) REFERENCES Passenger(PassengerId),
	Weight int
);

CREATE TABLE BusCompany(
	CompanyId bigint PRIMARY KEY,
	Name text
);

-- Passenger + BusCompany (n:m relationship)
CREATE TABLE Feedback(
	PassengerId bigint, 
	FOREIGN KEY (PassengerId) REFERENCES Passenger(PassengerId),
	CompanyId bigint, 
	FOREIGN KEY (CompanyId) REFERENCES BusCompany(CompanyId),
	Review text,
	CONSTRAINT fk_Feedback PRIMARY KEY (PassengerId, CompanyId)
);

-- 1:1 relationship with Driver
CREATE TABLE Bus(
	BusId bigint PRIMARY KEY,
	CompanyId bigint, 
	FOREIGN KEY (CompanyId) REFERENCES BusCompany(CompanyId),
	DriverId bigint UNIQUE,
	FOREIGN KEY (DriverId) REFERENCES Driver(DriverId),
	ModelName text
);

-- Passenger + Bus (2 1:n relationships)
CREATE TABLE Ticket(
	TicketId bigint PRIMARY KEY,
	PassengerId bigint, 
	FOREIGN KEY (PassengerId) REFERENCES Passenger(PassengerId),
	BusId bigint,
	FOREIGN KEY (BusId) REFERENCES Bus(BusId),
	BoardingTime time,
	Price bigint
);

CREATE TABLE City(
	CityId bigint PRIMARY KEY,
	Name text,
	Population int
);

CREATE TABLE BusStation(
	StationId bigint PRIMARY KEY,
	CityId bigint, 
	FOREIGN KEY (CityId) REFERENCES City(CityId),
	Name text
);

-- Bus + BusStation (n:m relationship)
CREATE TABLE BusStop(
	StopTime time,
	BusId bigint, 
	FOREIGN KEY (BusId) REFERENCES Bus(BusId),
	StationId bigint, 
	FOREIGN KEY (StationId) REFERENCES BusStation(StationId),
	CONSTRAINT pk_BusStop PRIMARY KEY (BusId, StationId)
);
