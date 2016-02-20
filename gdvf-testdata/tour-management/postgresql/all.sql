CREATE TYPE tm.gender AS ENUM ('male', 'female');

CREATE TABLE tm.profession (
	professionId INT NOT NULL,
	name TEXT NOT NULL,

	PRIMARY KEY (professionId)
);

CREATE TABLE tm.location (
	locationId INT NOT NULL,
	name TEXT NOT NULL,

	PRIMARY KEY (locationId)
);

CREATE TABLE tm.traveller (
	travellerId BIGINT NOT NULL,
	name TEXT NOT NULL,
	gender tm.gender NOT NULL,
	age INT NOT NULL CHECK ( age > 0 ),
	professionId INT NOT NULL REFERENCES tm.profession (professionId),

	PRIMARY KEY (travellerId)
);

CREATE TABLE tm.employee (
	employeeId BIGINT NOT NULL,
	name TEXT NOT NULL,
	gender tm.gender NOT NULL,
	age INT NOT NULL CHECK ( age > 0 ),

	PRIMARY KEY (employeeId)
);

CREATE TABLE tm.tour (
	tourId BIGINT NOT NULL,
	startTime TIMESTAMP NOT NULL,
	endTime TIMESTAMP NOT NULL,
	costPerPerson REAL NOT NULL,
	planOwnerId INT NOT NULL REFERENCES tm.employee (employeeId),

	PRIMARY KEY (tourId)
);

CREATE TABLE tm.tourpath (
	pathId BIGINT NOT NULL,
	startTime TIMESTAMP NOT NULL,
	endTime TIMESTAMP NOT NULL,
	tourId INT NOT NULL REFERENCES tm.tour (tourId),
	locationId INT NOT NULL REFERENCES tm.location (locationId),

	PRIMARY KEY (pathId)
);

CREATE TABLE tm.tourtraveller (
	tourtravellerId BIGINT NOT NULL,
	tourId INT NOT NULL REFERENCES tm.tour (tourId),
	travellerId INT NOT NULL REFERENCES tm.traveller (travellerId),

	PRIMARY KEY (tourtravellerId)
);

CREATE TABLE tm.travellerfeedback (
	travellerfeedbackId BIGINT NOT NULL,
	tourId INT NOT NULL REFERENCES tm.tour (tourId),
	travellerId INT NOT NULL REFERENCES tm.traveller (travellerId),
	feedback TEXT NOT NULL,

	PRIMARY KEY (travellerfeedbackId)
);
