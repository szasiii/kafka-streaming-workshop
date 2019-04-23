CREATE TABLE doctors
(
    id             INTEGER PRIMARY KEY,
    clinicId       INTEGER,
    name           VARCHAR(10),
    specialityCode VARCHAR(10)
);
CREATE TABLE clinics
(
    id     INTEGER PRIMARY KEY,
    name   VARCHAR(10),
    region VARCHAR(10)
);
CREATE TABLE medicines
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(10)
);
CREATE TABLE diseases
(
    id             INTEGER PRIMARY KEY,
    specialityCode VARCHAR(10),
    name           VARCHAR(10)
);
CREATE TABLE prescriptions
(
    id        INTEGER PRIMARY KEY,
    doctorId  INTEGER,
    diseaseId INTEGER,
    date      TIMESTAMP
);


INSERT INTO workshop.public.doctors
VALUES (1000, 'bambi')