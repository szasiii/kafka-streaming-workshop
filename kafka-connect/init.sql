CREATE TABLE doctors
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(10)
);
CREATE TABLE diseases
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(10)
);
CREATE TABLE prescriptions
(
    id           INTEGER PRIMARY KEY,
    doctorId     INTEGER,
    diseaseId    INTEGER,
    medicineName VARCHAR(100)
);


INSERT INTO workshop.public.doctors
VALUES (1000, 'bambi');

INSERT INTO workshop.public.doctors
VALUES (1001, 'godzilla');

INSERT INTO diseases
VALUES (1000, 'hiv');
INSERT INTO prescriptions
VALUES (1000, 1000, 1000, 'C');
INSERT INTO prescriptions
VALUES (1001, 1001, 1000, 'B');