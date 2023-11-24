-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: enjoytrip
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(255) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `registered_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `profile_image` mediumblob,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'oh','$2a$10$DeU8ipc6kb5Eb3goOUn05OyokNlXWkjQjEVzGEd/c2Ko.eonIBFb6','asdf@ssafy.com','USER','2023-11-14 01:28:37',NULL),(2,'admin','$2a$10$oRpYH.2W3UKbLHUt0UG20uWvoW8JAMQzy6.9ZRFOvDR9bMiniyHdy','admin@ssafy.com','ADMIN','2023-11-14 05:26:31',NULL),(3,'admin2','$2a$10$4xWLc93SvPUhKZ3QMQLjy.tVk.g9AbPQmXJV9yPR7ktccOobcKn1y','admin2@ssafy.com','ADMIN','2023-11-14 05:39:36',NULL),(4,'user2','$2a$10$U34Gbfzfa4yb1aPk5NFl0ePzemY5VQ23iVtBMoy/lIujtdg4M6yHS','user2@ssafy.com','ADMIN','2023-11-14 08:51:12',NULL),(5,'vue','$2a$10$ZJ4hXuRZIhb1FsLQD9o1MujfAhIjmLhvzdRChO.KzbYWwlq7h3z92','vue@ssafy.com','ADMIN','2023-11-16 02:26:57',NULL),(6,'ohoh','$2a$10$qohlaBiW75RkkgoFkSreX.ncLT7JclqRloUSYahip2avcj63SdPjW','oh','ADMIN','2023-11-16 05:12:39',NULL),(7,'test','$2a$10$NtVdRWsW.Yq2tvkulXD3o.vHsXyGO6boDO8nIUiPFQ.B3Bvm2PT.a','test@ssafy.com','USER','2023-11-16 05:14:06',NULL),(8,'pohoh','$2a$10$NkWVlLPr6HsgdaDSedjNTubAdfOM4DfxTEpbB7ejn5RSnJOmJ1T8O','oh@asdf','USER','2023-11-16 05:55:34',NULL),(9,'asdf','1234','asdf@asdf1234','USER','2023-11-20 00:09:38',NULL),(10,'dd','$2a$10$YZaVc1DGHCH7tJeRAvl0BuN27ys5ii0rmoA2QTwg.J2R1KaPne8Du','dd@dd','USER','2023-11-23 08:38:00',NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-24 12:28:39
