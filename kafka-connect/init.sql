CREATE TABLE doctors (id INTEGER PRIMARY KEY, clinicId INTEGER, name VARCHAR(10), specialityCode VARCHAR(10));
CREATE TABLE clinic(id INTEGER PRIMARY KEY, name VARCHAR(10), region VARCHAR(10));
CREATE TABLE medicine (id INTEGER PRIMARY KEY, name VARCHAR(10));
CREATE TABLE disease (id INTEGER PRIMARY KEY, specialityCode VARCHAR(10), name VARCHAR(10));
CREATE TABLE prescription (id INTEGER PRIMARY KEY, doctorId INTEGER, diseaseId INTEGER, date TIMESTAMP);




INSERT INTO workshop.public.doctors VALUES (1000, 'bambi')