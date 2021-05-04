DROP TABLE TICKETS;
DROP TABLE SCHEDULES;
DROP TABLE TRIPS;
DROP TABLE CLIENTS;
DROP TABLE TRAIN_COMPOSITIONS;
DROP TABLE CARRIAGE_TYPES;
DROP TABLE TRAINS;
DROP TABLE STATIONS;

CREATE TABLE STATIONS (
id INT NOT NULL,
station_name VARCHAR(35) NOT NULL,
CONSTRAINT stations_pk PRIMARY KEY (id)
);

CREATE TABLE TRAINS (
id INT NOT NULL,
train_type VARCHAR(35) NOT NULL,
issue_year DATE NOT NULL,
CONSTRAINT trains_pk PRIMARY KEY (id)
);

CREATE TABLE CARRIAGE_TYPES (
id INT NOT NULL,
blocks_number INT NOT NULL,
block_seats_number INT NOT NULL,
price INT NOT NULL,
CONSTRAINT carriage_types_pk PRIMARY KEY (id)
);

CREATE TABLE TRAIN_COMPOSITIONS (
id INT NOT NULL,
train_id INT NOT NULL,
carriage_type_id INT NOT NULL,
carriage_number INT NOT NULL,
CONSTRAINT trains_compositions_pk PRIMARY KEY (id),
CONSTRAINT compositions_train_fk FOREIGN KEY (train_id) REFERENCES trains(id),
CONSTRAINT compositions_carriage_fk FOREIGN KEY (carriage_type_id) REFERENCES carriage_types(id)
);

CREATE TABLE CLIENTS (
id INT NOT NULL,
email VARCHAR(35) NOT NULL CONSTRAINT clients_email_len CHECK (email LIKE '%@%'),
user_password VARCHAR(35) NOT NULL,
passport BIGINT CONSTRAINT clients_passport_len CHECK (passport > 999999999),
last_name VARCHAR(35) NOT NULL,
first_name VARCHAR(35) NOT NULL,
patronymic VARCHAR(35),
date_of_birth DATE NOT NULL,
phone_number BIGINT NOT NULL CONSTRAINT clients_phone_len CHECK (phone_number > 9999999999),
CONSTRAINT clients_pk PRIMARY KEY (id)
);

CREATE TABLE TRIPS (
id INT NOT NULL,
departure_station_id INT NOT NULL,
destination_station_id INT NOT NULL,
CONSTRAINT trips_pk PRIMARY KEY (id),
CONSTRAINT trips_departure_fk FOREIGN KEY (departure_station_id) REFERENCES stations(id),
CONSTRAINT trips_destination_fk FOREIGN KEY (destination_station_id) REFERENCES stations(id)
);

CREATE TABLE SCHEDULES (
id INT NOT NULL,
trip_id INT NOT NULL,
date_and_time TIMESTAMP NOT NULL,
train_id INT NOT NULL,
platform INT,
CONSTRAINT schedules_pk PRIMARY KEY (id),
CONSTRAINT schedules_trip_fk FOREIGN KEY (trip_id) REFERENCES trips(id),
CONSTRAINT schedules_train_fk FOREIGN KEY (train_id) REFERENCES trains(id)
);

CREATE TABLE TICKETS (
id INT NOT NULL,
client_id INT NOT NULL,
schedule_id INT NOT NULL,
railway_carriage INT NOT NULL,
place INT NOT NULL,
price INT NOT NULL,
CONSTRAINT tickets_pk PRIMARY KEY (id),
CONSTRAINT tickets_client_fk FOREIGN KEY (client_id) REFERENCES clients(id),
CONSTRAINT tickets_schedule_fk FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);



INSERT INTO STATIONS (id, station_name) VALUES (1, 'Санкт-Петербург');
INSERT INTO STATIONS (id, station_name) VALUES (2, 'Великий Новгород');
INSERT INTO STATIONS (id, station_name) VALUES (3, 'Валдай');
INSERT INTO STATIONS (id, station_name) VALUES (4, 'Вышний волочек');
INSERT INTO STATIONS (id, station_name) VALUES (5, 'Тверь');
INSERT INTO STATIONS (id, station_name) VALUES (6, 'Клин');
INSERT INTO STATIONS (id, station_name) VALUES (7, 'Москва');
INSERT INTO STATIONS (id, station_name) VALUES (8, 'Коломна');
INSERT INTO STATIONS (id, station_name) VALUES (9, 'Рязань');
INSERT INTO STATIONS (id, station_name) VALUES (10, 'Ряжск');
INSERT INTO STATIONS (id, station_name) VALUES (11, 'Тамбов');
INSERT INTO STATIONS (id, station_name) VALUES (12, 'Борисоглебск');
INSERT INTO STATIONS (id, station_name) VALUES (13, 'Михайловка');
INSERT INTO STATIONS (id, station_name) VALUES (14, 'Волгоград');
INSERT INTO STATIONS (id, station_name) VALUES (15, 'Ахтубинск');
INSERT INTO STATIONS (id, station_name) VALUES (16, 'Харабали');
INSERT INTO STATIONS (id, station_name) VALUES (17, 'Астрахань');

INSERT INTO TRAINS (id, train_type, issue_year) VALUES (760, 'Поезд', STR_TO_DATE('01.01.2015', '%d.%m.%Y'));
INSERT INTO TRAINS (id, train_type, issue_year) VALUES (86, 'Электричка', STR_TO_DATE('01.01.2017', '%d.%m.%Y'));
INSERT INTO TRAINS (id, train_type, issue_year) VALUES (783, 'Поезд', STR_TO_DATE('01.01.2019', '%d.%m.%Y'));
INSERT INTO TRAINS (id, train_type, issue_year) VALUES (115, 'Поезд', STR_TO_DATE('01.01.2014', '%d.%m.%Y'));
INSERT INTO TRAINS (id, train_type, issue_year) VALUES (137, 'Электричка', STR_TO_DATE('01.01.2020', '%d.%m.%Y'));

INSERT INTO CARRIAGE_TYPES (id, blocks_number, block_seats_number, price) VALUES (1, 9, 2, 5000);
INSERT INTO CARRIAGE_TYPES (id, blocks_number, block_seats_number, price) VALUES (2, 9, 4, 3000);
INSERT INTO CARRIAGE_TYPES (id, blocks_number, block_seats_number, price) VALUES (3, 9, 6, 1500);
INSERT INTO CARRIAGE_TYPES (id, blocks_number, block_seats_number, price) VALUES (4, 9, 9, 800);

INSERT INTO TRAIN_COMPOSITIONS (id, train_id, carriage_type_id, carriage_number) VALUES (1, 760, 2, 4);
INSERT INTO TRAIN_COMPOSITIONS (id, train_id, carriage_type_id, carriage_number) VALUES (2, 760, 3, 6);
INSERT INTO TRAIN_COMPOSITIONS (id, train_id, carriage_type_id, carriage_number) VALUES (3, 783, 1, 2);
INSERT INTO TRAIN_COMPOSITIONS (id, train_id, carriage_type_id, carriage_number) VALUES (4, 783, 2, 3);
INSERT INTO TRAIN_COMPOSITIONS (id, train_id, carriage_type_id, carriage_number) VALUES (5, 783, 3, 5);
INSERT INTO TRAIN_COMPOSITIONS (id, train_id, carriage_type_id, carriage_number) VALUES (6, 115, 3, 8);
INSERT INTO TRAIN_COMPOSITIONS (id, train_id, carriage_type_id, carriage_number) VALUES (7, 115, 4, 2);

INSERT INTO CLIENTS (id, email, user_password, passport, last_name, first_name, patronymic, date_of_birth, phone_number)
VALUES (1, 'kozh01@mail.ru', 11111111, 4486976376, 'Козарь', 'Карина', 'Федоровна', STR_TO_DATE('05.09.1966', '%d.%m.%Y'), 89595631754);
INSERT INTO CLIENTS (id, email, user_password, passport, last_name, first_name, patronymic, date_of_birth, phone_number)
VALUES (2, 'alex007@gmail.com', 22222222, 4756856424, 'Трутнев', 'Алексей', 'Наумович', STR_TO_DATE('02.05.1962', '%d.%m.%Y'), 89803939258);
INSERT INTO CLIENTS (id, email, user_password, passport, last_name, first_name, patronymic, date_of_birth, phone_number)
VALUES (3, 'yaya@ya.ru', 33333333, 4615640038, 'Яков', 'Игнатий', 'Валентинович', STR_TO_DATE('20.01.1980', '%d.%m.%Y'), 89237977739);

INSERT INTO TRIPS (id, departure_station_id, destination_station_id) VALUES (1, 7, 1);
INSERT INTO TRIPS (id, departure_station_id, destination_station_id) VALUES (2, 7, 17);
INSERT INTO TRIPS (id, departure_station_id, destination_station_id) VALUES (3, 17, 14);
INSERT INTO TRIPS (id, departure_station_id, destination_station_id) VALUES (4, 7, 5);
INSERT INTO TRIPS (id, departure_station_id, destination_station_id) VALUES (5, 10, 15);
INSERT INTO TRIPS (id, departure_station_id, destination_station_id) VALUES (6, 5, 1);

INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (1, 1, STR_TO_DATE('20.01.2021 03:00:00', '%d.%m.%Y %T'), 783, 1);
INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (2, 2, STR_TO_DATE('20.01.2021 14:50:00', '%d.%m.%Y %T'), 760, 1);
INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (3, 2, STR_TO_DATE('11.07.2021 05:40:00', '%d.%m.%Y %T'), 760, 2);
INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (4, 3, STR_TO_DATE('06.08.2021 10:25:00', '%d.%m.%Y %T'), 783, 3);
INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (5, 1, STR_TO_DATE('21.08.2021 17:40:00', '%d.%m.%Y %T'), 115, 1);
INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (6, 4, STR_TO_DATE('21.01.2021 03:00:00', '%d.%m.%Y %T'), 115, 1);
INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (7, 5, STR_TO_DATE('07.03.2021 14:30:00', '%d.%m.%Y %T'), 760, 1);
INSERT INTO SCHEDULES (id, trip_id, date_and_time, train_id, platform)
VALUES (8, 6, STR_TO_DATE('05.02.2021 05:45:00', '%d.%m.%Y %T'), 115, 2);

INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (1, 1, 2, 3, 74, 5000);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (2, 2, 2, 4, 120, 2700);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (3, 1, 1, 2, 25, 5000);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (4, 2, 4, 1, 10, 5000);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (5, 1, 4, 3, 47, 5000);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (6, 2, 4, 4, 80, 3100);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (7, 1, 3, 7, 242, 2000);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (8, 2, 8, 9, 500, 1500);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (9, 1, 6, 8, 414, 2000);
INSERT INTO TICKETS (id, client_id, schedule_id, railway_carriage, place, price)
VALUES (10, 2, 7, 6, 230, 2200);