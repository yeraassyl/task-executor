-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: localhost    Database: kaspilab
-- ------------------------------------------------------
-- Server version	5.7.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BLACKLIST`
--

DROP TABLE IF EXISTS `BLACKLIST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BLACKLIST` (
  `IIN` varchar(100) CHARACTER SET latin1 NOT NULL,
  `FIRST_NAME` varchar(100) CHARACTER SET latin1 NOT NULL,
  `LAST_NAME` varchar(100) CHARACTER SET latin1 NOT NULL,
  `PATRONYMIC` varchar(100) CHARACTER SET latin1 NOT NULL,
  `BLACKLIST_REASON` text CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`IIN`),
  UNIQUE KEY `BLACKLIST_UN` (`IIN`),
  UNIQUE KEY `BLACKLIST_UN1` (`FIRST_NAME`,`LAST_NAME`,`PATRONYMIC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BLACKLIST`
--

LOCK TABLES `BLACKLIST` WRITE;
/*!40000 ALTER TABLE `BLACKLIST` DISABLE KEYS */;
INSERT INTO `BLACKLIST` VALUES ('000710500032','Yerassyl','Bakytnuruly','Bakytnuruly','Credit outdated');
/*!40000 ALTER TABLE `BLACKLIST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `D_CALC`
--

DROP TABLE IF EXISTS `D_CALC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `D_CALC` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `type` int(11) NOT NULL,
  `script` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `D_CALC_FK` (`type`),
  CONSTRAINT `D_CALC_FK` FOREIGN KEY (`type`) REFERENCES `D_CALC_TYPES` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `D_CALC`
--

LOCK TABLES `D_CALC` WRITE;
/*!40000 ALTER TABLE `D_CALC` DISABLE KEYS */;
INSERT INTO `D_CALC` VALUES (1,'test',1,'lab1/test.groovy'),(2,'test1',2,'lab1/test1.sql'),(3,'test2',2,'lab1/test2.sql'),(4,'test3',3,NULL),(5,'test4',2,'lab2/test3.sql'),(6,'test5',3,NULL);
/*!40000 ALTER TABLE `D_CALC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `D_CALC_DEC`
--

DROP TABLE IF EXISTS `D_CALC_DEC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `D_CALC_DEC` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calc_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL,
  `par` int(11) NOT NULL,
  `op` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `const` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `result` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `D_CALC_DEC_FK` (`calc_id`),
  KEY `D_CALC_DEC_FK_1` (`par`),
  CONSTRAINT `D_CALC_DEC_FK` FOREIGN KEY (`calc_id`) REFERENCES `D_CALC` (`id`),
  CONSTRAINT `D_CALC_DEC_FK_1` FOREIGN KEY (`par`) REFERENCES `D_PAR` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `D_CALC_DEC`
--

LOCK TABLES `D_CALC_DEC` WRITE;
/*!40000 ALTER TABLE `D_CALC_DEC` DISABLE KEYS */;
INSERT INTO `D_CALC_DEC` VALUES (1,4,1,8,'!=','','Найдено совпадение по ИИН'),(2,4,2,9,'!=','','Найдено совпадение по ФИО'),(3,4,3,8,'==','','Совпадений по ИИН или ФИО не найдено'),(4,6,1,12,'!=','','Найдено совпадение'),(5,6,2,12,'==',' ','Совпадений по ИИН или ФИО не найдено');
/*!40000 ALTER TABLE `D_CALC_DEC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `D_CALC_TYPES`
--

DROP TABLE IF EXISTS `D_CALC_TYPES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `D_CALC_TYPES` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `D_CALC_TYPES`
--

LOCK TABLES `D_CALC_TYPES` WRITE;
/*!40000 ALTER TABLE `D_CALC_TYPES` DISABLE KEYS */;
INSERT INTO `D_CALC_TYPES` VALUES (1,'GROOVY'),(2,'SQL'),(3,'DECISION_TAB');
/*!40000 ALTER TABLE `D_CALC_TYPES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `D_PAR`
--

DROP TABLE IF EXISTS `D_PAR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `D_PAR` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `json_path` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `proc` int(11) NOT NULL,
  `type` varchar(100) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `D_PAR_FK_1` (`proc`),
  CONSTRAINT `D_PAR_FK_1` FOREIGN KEY (`proc`) REFERENCES `D_PROC` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `D_PAR`
--

LOCK TABLES `D_PAR` WRITE;
/*!40000 ALTER TABLE `D_PAR` DISABLE KEYS */;
INSERT INTO `D_PAR` VALUES (1,'IIN','/IIN',1,'TEXT'),(2,'FIRST_NAME','/FIRST_NAME',1,'TEXT'),(3,'LAST_NAME','/LAST_NAME',1,'TEXT'),(4,'PATRONYMIC','/PATRONYMIC',1,'TEXT'),(5,'test','/test',1,'TEXT'),(6,'test1','/test1',1,'SIMPLE'),(7,'test2','/test2',1,'SIMPLE'),(8,'BLACKLIST_REASON_IIN','/test1/BLACKLIST_REASON',1,'TEXT'),(9,'BLACKLIST_REASON_FN','/test2/BLACKLIST_REASON',1,'TEXT'),(10,'test3','/test3',1,'TEXT'),(11,'test4','/test4',2,'SIMPLE'),(12,'BLACKLIST_REASON','/test4/BLACKLIST_REASON',2,'TEXT'),(13,'IIN','/IIN',2,'TEXT'),(14,'FIRST_NAME','/FIRST_NAME',2,'TEXT'),(15,'LAST_NAME','/LAST_NAME',2,'TEXT'),(16,'PATRONYMIC','/PATRONYMIC',2,'TEXT'),(17,'test','/test',2,'TEXT'),(18,'test5','/test5',2,'TEXT');
/*!40000 ALTER TABLE `D_PAR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `D_PROC`
--

DROP TABLE IF EXISTS `D_PROC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `D_PROC` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `D_PROC`
--

LOCK TABLES `D_PROC` WRITE;
/*!40000 ALTER TABLE `D_PROC` DISABLE KEYS */;
INSERT INTO `D_PROC` VALUES (1,'blacklist_lab1'),(2,'blacklist_lab2');
/*!40000 ALTER TABLE `D_PROC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `D_PROC_CALC`
--

DROP TABLE IF EXISTS `D_PROC_CALC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `D_PROC_CALC` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `proc` int(11) NOT NULL,
  `calc` int(11) NOT NULL,
  `ord` int(11) NOT NULL,
  `flag` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `D_PROC_CALC_FK_1` (`calc`),
  KEY `D_PROC_CALC_FK` (`proc`),
  CONSTRAINT `D_PROC_CALC_FK` FOREIGN KEY (`proc`) REFERENCES `D_PROC` (`id`),
  CONSTRAINT `D_PROC_CALC_FK_1` FOREIGN KEY (`calc`) REFERENCES `D_CALC` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `D_PROC_CALC`
--

LOCK TABLES `D_PROC_CALC` WRITE;
/*!40000 ALTER TABLE `D_PROC_CALC` DISABLE KEYS */;
INSERT INTO `D_PROC_CALC` VALUES (1,1,1,1,0),(2,1,3,3,0),(3,1,2,2,0),(4,1,4,4,1),(5,2,1,1,0),(6,2,5,2,0),(7,2,6,3,1);
/*!40000 ALTER TABLE `D_PROC_CALC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `err_log`
--

DROP TABLE IF EXISTS `err_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `err_log` (
  `datetime` datetime NOT NULL,
  `error_message` text COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `err_log`
--

LOCK TABLES `err_log` WRITE;
/*!40000 ALTER TABLE `err_log` DISABLE KEYS */;
INSERT INTO `err_log` VALUES ('2020-05-26 23:12:09','НЕВОЗМОЖНО ВЫПОЛНИТЬ ПО ПРИЧИНЕ:\n JAVAX.SCRIPT.SCRIPTEXCEPTION: JAVA.LANG.EXCEPTION: НЕДОСТАТОЧНО ДАННЫХ ДЛЯ ДАЛЬНЕЙШЕЙ ОБРАБОТКИ'),('2020-05-26 23:22:50','JSON PATH ДЛЯ IIN НЕ СУЩЕСТВУЕТ'),('2020-05-26 23:24:53','JSON PATH ДЛЯ IIN НЕ СУЩЕСТВУЕТ'),('2020-05-26 23:27:32','Не найден терминируюший флаг для процесса 2');
/*!40000 ALTER TABLE `err_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ext_log`
--

DROP TABLE IF EXISTS `ext_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ext_log` (
  `receive_time` datetime NOT NULL,
  `input` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `response_time` datetime NOT NULL,
  `output` text COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ext_log`
--

LOCK TABLES `ext_log` WRITE;
/*!40000 ALTER TABLE `ext_log` DISABLE KEYS */;
INSERT INTO `ext_log` VALUES ('2020-05-26 23:05:42','{\"FIRST_NAME\": \"Yerassyl\", \"LAST_NAME\": \"Bakytnuruly\", \"PATRONYMIC\": \"Bakytnuruly\"}','2020-05-26 23:05:44','Найдено совпадение по ФИО'),('2020-05-26 23:11:44','{\"FIRST_NAME\": \"Yerassyl\", \"LAST_NAME\": \"Bakytnuruly\", \"PATRONYMIC\": \"Bakytnuruly\"}','2020-05-26 23:11:47','Найдено совпадение по ФИО'),('2020-05-26 23:12:16','{\"FIRST_NAME\": \"Yerassyl\", \"LAST_NAME\": \"Bakytnuruly\", \"PATRONYMIC\": \"asdf\"}','2020-05-26 23:12:16','Совпадений по ИИН или ФИО не найдено'),('2020-05-26 23:29:54','{\"FIRST_NAME\": \"Yerassyl\", \"LAST_NAME\": \"Bakytnuruly\", \"PATRONYMIC\": \"Bakytnuruly\"}','2020-05-26 23:29:54','Найдено совпадение'),('2020-05-26 23:30:22','{\"IIN\": \"000710500032\"}','2020-05-26 23:30:22','Найдено совпадение'),('2020-05-26 23:31:16','{\"IIN\": \"000710500032\"}','2020-05-26 23:31:17','Найдено совпадение'),('2020-05-26 23:31:22','{\"IIN\": \"000710500032\"}','2020-05-26 23:31:22','Найдено совпадение по ИИН');
/*!40000 ALTER TABLE `ext_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proc_log`
--

DROP TABLE IF EXISTS `proc_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proc_log` (
  `datetime` datetime NOT NULL,
  `log` text COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proc_log`
--

LOCK TABLES `proc_log` WRITE;
/*!40000 ALTER TABLE `proc_log` DISABLE KEYS */;
INSERT INTO `proc_log` VALUES ('2020-05-26 23:05:44','Evaluating calculation test of process 1'),('2020-05-26 23:05:44','Successful evaluation test of process 1'),('2020-05-26 23:05:44','Evaluating calculation test1 of process 1'),('2020-05-26 23:05:44','Successful evaluation test1 of process 1'),('2020-05-26 23:05:44','Evaluating calculation test2 of process 1'),('2020-05-26 23:05:44','Successful evaluation test2 of process 1'),('2020-05-26 23:05:44','Evaluating calculation test3 of process 1'),('2020-05-26 23:05:44','Successful evaluation test3 of process 1'),('2020-05-26 23:11:45','Evaluating calculation test of process 1'),('2020-05-26 23:11:46','Successful evaluation test of process 1'),('2020-05-26 23:11:46','Evaluating calculation test1 of process 1'),('2020-05-26 23:11:46','Successful evaluation test1 of process 1'),('2020-05-26 23:11:46','Evaluating calculation test2 of process 1'),('2020-05-26 23:11:47','Successful evaluation test2 of process 1'),('2020-05-26 23:11:47','Evaluating calculation test3 of process 1'),('2020-05-26 23:11:47','Successful evaluation test3 of process 1'),('2020-05-26 23:12:09','Evaluating calculation test of process 1'),('2020-05-26 23:12:16','Evaluating calculation test of process 1'),('2020-05-26 23:12:16','Successful evaluation test of process 1'),('2020-05-26 23:12:16','Evaluating calculation test1 of process 1'),('2020-05-26 23:12:16','Successful evaluation test1 of process 1'),('2020-05-26 23:12:16','Evaluating calculation test2 of process 1'),('2020-05-26 23:12:16','Successful evaluation test2 of process 1'),('2020-05-26 23:12:16','Evaluating calculation test3 of process 1'),('2020-05-26 23:12:16','Successful evaluation test3 of process 1'),('2020-05-26 23:27:32','Evaluating calculation test of process 2'),('2020-05-26 23:27:32','Successful evaluation test of process 2'),('2020-05-26 23:27:32','Evaluating calculation test4 of process 2'),('2020-05-26 23:27:32','Successful evaluation test4 of process 2'),('2020-05-26 23:27:32','Evaluating calculation test5 of process 2'),('2020-05-26 23:27:32','Successful evaluation test5 of process 2'),('2020-05-26 23:29:54','Evaluating calculation test of process 2'),('2020-05-26 23:29:54','Successful evaluation test of process 2'),('2020-05-26 23:29:54','Evaluating calculation test4 of process 2'),('2020-05-26 23:29:54','Successful evaluation test4 of process 2'),('2020-05-26 23:29:54','Evaluating calculation test5 of process 2'),('2020-05-26 23:29:54','Successful evaluation test5 of process 2'),('2020-05-26 23:30:22','Evaluating calculation test of process 2'),('2020-05-26 23:30:22','Successful evaluation test of process 2'),('2020-05-26 23:30:22','Evaluating calculation test4 of process 2'),('2020-05-26 23:30:22','Successful evaluation test4 of process 2'),('2020-05-26 23:30:22','Evaluating calculation test5 of process 2'),('2020-05-26 23:30:22','Successful evaluation test5 of process 2'),('2020-05-26 23:31:17','Evaluating calculation test of process 2'),('2020-05-26 23:31:17','Successful evaluation test of process 2'),('2020-05-26 23:31:17','Evaluating calculation test4 of process 2'),('2020-05-26 23:31:17','Successful evaluation test4 of process 2'),('2020-05-26 23:31:17','Evaluating calculation test5 of process 2'),('2020-05-26 23:31:17','Successful evaluation test5 of process 2'),('2020-05-26 23:31:22','Evaluating calculation test of process 1'),('2020-05-26 23:31:22','Successful evaluation test of process 1'),('2020-05-26 23:31:22','Evaluating calculation test2 of process 1'),('2020-05-26 23:31:22','Successful evaluation test2 of process 1'),('2020-05-26 23:31:22','Evaluating calculation test1 of process 1'),('2020-05-26 23:31:22','Successful evaluation test1 of process 1'),('2020-05-26 23:31:22','Evaluating calculation test3 of process 1'),('2020-05-26 23:31:22','Successful evaluation test3 of process 1');
/*!40000 ALTER TABLE `proc_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-26 23:34:22
