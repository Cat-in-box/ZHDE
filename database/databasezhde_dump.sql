-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: databasezhde
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carriage_types`
--

DROP TABLE IF EXISTS `carriage_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carriage_types` (
  `id` int NOT NULL,
  `blocks_number` int NOT NULL,
  `block_seats_number` int NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carriage_types`
--

LOCK TABLES `carriage_types` WRITE;
/*!40000 ALTER TABLE `carriage_types` DISABLE KEYS */;
INSERT INTO `carriage_types` VALUES (1,9,2,5000),(2,9,4,3000),(3,9,6,1500),(4,9,9,800);
/*!40000 ALTER TABLE `carriage_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` int NOT NULL,
  `email` varchar(35) NOT NULL,
  `user_password` varchar(35) NOT NULL,
  `passport` bigint DEFAULT NULL,
  `last_name` varchar(35) NOT NULL,
  `first_name` varchar(35) NOT NULL,
  `patronymic` varchar(35) DEFAULT NULL,
  `date_of_birth` date NOT NULL,
  `phone_number` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `clients_email_len` CHECK ((`email` like _utf8mb4'%@%')),
  CONSTRAINT `clients_passport_len` CHECK ((`passport` > 999999999)),
  CONSTRAINT `clients_phone_len` CHECK ((`phone_number` > 9999999999))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'kozh01@mail.ru','11111111',4486976376,'Козарь','Карина','Федоровна','1966-09-05',89595631754),(2,'alex007@gmail.com','22222222',4756856424,'Трутнев','Алексей','Наумович','1962-05-02',89803939258),(3,'yaya@ya.ru','33333333',4615640038,'Яков','Игнатий','Валентинович','1980-01-20',89237977739),(4,'dsfg@dafsdgfg','1w2e3r4t',1234567890,'Demo','Demo','','2000-01-01',12345678910),(5,'meow@yu.ru','12345678',1111222333,'Demo1','Demo1','Demoo1','2020-02-20',12223334455),(6,'1111@mail.ru','12121212',1111111111,'Demo1','Demo1','Demo','2001-01-01',12223334455);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedules` (
  `id` int NOT NULL,
  `trip_id` int NOT NULL,
  `date_and_time` timestamp NOT NULL,
  `train_id` int NOT NULL,
  `platform` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `schedules_trip_fk` (`trip_id`),
  KEY `schedules_train_fk` (`train_id`),
  CONSTRAINT `schedules_train_fk` FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`),
  CONSTRAINT `schedules_trip_fk` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
INSERT INTO `schedules` VALUES (1,1,'2021-01-20 00:00:00',783,1),(2,2,'2021-01-20 11:50:00',760,1),(3,2,'2021-07-11 02:40:00',760,2),(4,3,'2021-08-06 07:25:00',783,3),(5,1,'2021-08-21 14:40:00',115,1),(6,4,'2021-01-21 00:00:00',115,1),(7,5,'2021-03-07 11:30:00',760,1),(8,6,'2021-02-05 02:45:00',115,2);
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `id` int NOT NULL,
  `station_name` varchar(35) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'Санкт-Петербург'),(2,'Великий Новгород'),(3,'Валдай'),(4,'Вышний волочек'),(5,'Тверь'),(6,'Клин'),(7,'Москва'),(8,'Коломна'),(9,'Рязань'),(10,'Ряжск'),(11,'Тамбов'),(12,'Борисоглебск'),(13,'Михайловка'),(14,'Волгоград'),(15,'Ахтубинск'),(16,'Харабали'),(17,'Астрахань');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` int NOT NULL,
  `client_id` int NOT NULL,
  `schedule_id` int NOT NULL,
  `railway_carriage` int NOT NULL,
  `place` int NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tickets_client_fk` (`client_id`),
  KEY `tickets_schedule_fk` (`schedule_id`),
  CONSTRAINT `tickets_client_fk` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `tickets_schedule_fk` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,1,2,3,74,5000),(2,2,2,4,120,2700),(3,1,1,2,25,5000),(4,2,4,1,10,5000),(5,1,4,3,47,5000),(6,2,4,4,80,3100),(7,1,3,7,242,2000),(8,2,8,9,500,1500),(9,1,6,8,414,2000),(10,2,7,6,230,2200);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train_compositions`
--

DROP TABLE IF EXISTS `train_compositions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train_compositions` (
  `id` int NOT NULL,
  `train_id` int NOT NULL,
  `carriage_type_id` int NOT NULL,
  `carriage_number` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `compositions_train_fk` (`train_id`),
  KEY `compositions_carriage_fk` (`carriage_type_id`),
  CONSTRAINT `compositions_carriage_fk` FOREIGN KEY (`carriage_type_id`) REFERENCES `carriage_types` (`id`),
  CONSTRAINT `compositions_train_fk` FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train_compositions`
--

LOCK TABLES `train_compositions` WRITE;
/*!40000 ALTER TABLE `train_compositions` DISABLE KEYS */;
INSERT INTO `train_compositions` VALUES (1,760,2,4),(2,760,3,6),(3,783,1,2),(4,783,2,3),(5,783,3,5),(6,115,3,8),(7,115,4,2);
/*!40000 ALTER TABLE `train_compositions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trains`
--

DROP TABLE IF EXISTS `trains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trains` (
  `id` int NOT NULL,
  `train_type` varchar(35) NOT NULL,
  `issue_year` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trains`
--

LOCK TABLES `trains` WRITE;
/*!40000 ALTER TABLE `trains` DISABLE KEYS */;
INSERT INTO `trains` VALUES (86,'Электричка','2017-01-01'),(115,'Поезд','2014-01-01'),(137,'Электричка','2020-01-01'),(760,'Поезд','2015-01-01'),(783,'Поезд','2019-01-01');
/*!40000 ALTER TABLE `trains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trips`
--

DROP TABLE IF EXISTS `trips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trips` (
  `id` int NOT NULL,
  `departure_station_id` int NOT NULL,
  `destination_station_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `trips_departure_fk` (`departure_station_id`),
  KEY `trips_destination_fk` (`destination_station_id`),
  CONSTRAINT `trips_departure_fk` FOREIGN KEY (`departure_station_id`) REFERENCES `stations` (`id`),
  CONSTRAINT `trips_destination_fk` FOREIGN KEY (`destination_station_id`) REFERENCES `stations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trips`
--

LOCK TABLES `trips` WRITE;
/*!40000 ALTER TABLE `trips` DISABLE KEYS */;
INSERT INTO `trips` VALUES (1,7,1),(2,7,17),(3,17,14),(4,7,5),(5,10,15),(6,5,1);
/*!40000 ALTER TABLE `trips` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-14 23:45:29
