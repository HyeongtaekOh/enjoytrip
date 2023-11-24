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
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `theme` varchar(50) NOT NULL,
  `departures_id` int NOT NULL,
  `departures_name` varchar(100) NOT NULL,
  `arrivals_id` int NOT NULL,
  `arrivals_name` varchar(100) NOT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `hit` bigint DEFAULT '0',
  PRIMARY KEY (`plan_id`),
  KEY `departures_fk` (`departures_id`),
  KEY `arrivals_fk` (`arrivals_id`),
  KEY `member_fk` (`user_id`),
  CONSTRAINT `arrivals_fk` FOREIGN KEY (`arrivals_id`) REFERENCES `attraction_info` (`content_id`),
  CONSTRAINT `departures_fk` FOREIGN KEY (`departures_id`) REFERENCES `attraction_info` (`content_id`),
  CONSTRAINT `member_fk` FOREIGN KEY (`user_id`) REFERENCES `member` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (1,1,'plan2','즐거운 여행','Trip',125266,'국립 청태산자연휴양림',125406,'비슬산자연휴양림','2023-11-17 06:36:35','2023-11-17 06:36:35',0),(3,2,'plan5','따라 떠나보자~!','City',1997652,'부평 기적의도서관',2019324,'이코노미호텔 인천부평점 [한국관광 품질인증/Korea Quality]','2023-11-21 05:25:16','2023-11-21 05:25:16',0),(4,9,'헤엄쳐서 와라 젠지','개헤엄','레포츠',125468,'마니산(강화)',125502,'강화도','2023-11-22 00:28:57','2023-11-22 00:28:57',0),(5,9,'강릉 또 가고싶다','맛있겠죠?','음식',133494,'원조초당순두부',135067,'무지개횟집','2023-11-22 00:31:36','2023-11-22 00:31:36',0),(6,9,'해운대~~~광안리에~~모인~~여러분들과~~','여행을 시~~~자~~~악~~~~~~~~~~하겠습니다아아아아아아아','축제',126081,'해운대해수욕장',130668,'벡스코(BEXCO)','2023-11-22 00:39:54','2023-11-22 00:39:54',0),(7,9,'부천 재미없어요','(진짜임)','기타',126704,'부천 중앙공원',133106,'손가면옥','2023-11-22 00:47:11','2023-11-22 00:47:11',0),(8,9,'맛있겠죠?','후루룹 짭짭','음식',133106,'손가면옥',403699,'대청마루뼈감자탕','2023-11-22 01:08:14','2023-11-22 01:08:14',0),(9,9,'Zig-Zag','슉슉슈슉 슈...','문화',125561,'인천향교',1617733,'무덕정','2023-11-22 03:43:35','2023-11-22 03:43:35',0),(10,9,'또 헤엄쳐라 젠지','그래도 중국 가면 안돼...','레포츠',125468,'마니산(강화)',125498,'승봉도 이일레 해변','2023-11-22 04:59:06','2023-11-22 04:59:06',0),(11,7,'하하하','히ㅣㅎ히asesaef','자연',125406,'비슬산자연휴양림',1997652,'부평 기적의도서관','2023-11-23 05:22:42','2023-11-23 08:46:10',0);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-24 12:28:37
