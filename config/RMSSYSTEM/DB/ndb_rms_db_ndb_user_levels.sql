CREATE DATABASE  IF NOT EXISTS `ndb_rms_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ndb_rms_db`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: ndb_rms_db
-- ------------------------------------------------------
-- Server version	5.5.9

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
-- Table structure for table `ndb_user_levels`
--

DROP TABLE IF EXISTS `ndb_user_levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ndb_user_levels` (
  `idndb_user_levels` int(11) NOT NULL AUTO_INCREMENT,
  `ndb_user_level` varchar(245) DEFAULT NULL,
  `ndb_user_level_status` varchar(245) DEFAULT NULL,
  `ndb_user_level_creat_by` varchar(245) DEFAULT NULL,
  `ndb_user_level_creat_date` datetime DEFAULT NULL,
  `ndb_user_level_mod_by` varchar(245) DEFAULT NULL,
  `ndb_user_level_mod_date` datetime DEFAULT NULL,
  PRIMARY KEY (`idndb_user_levels`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ndb_user_levels`
--

LOCK TABLES `ndb_user_levels` WRITE;
/*!40000 ALTER TABLE `ndb_user_levels` DISABLE KEYS */;
INSERT INTO `ndb_user_levels` VALUES (1,'PDC - RF Master File Inputter','ACTIVE','Madhawa_4799','2016-04-25 14:48:06','Madhawa_4799','2016-04-25 14:48:06'),(2,'PDC - RF Master File Authorizor','ACTIVE','Madhawa_4799','2016-04-25 14:48:29','Madhawa_4799','2016-04-25 14:48:29'),(3,'PDC - RF Transaction Inputter','ACTIVE','Madhawa_4799','2016-04-25 14:48:54','Madhawa_4799','2016-04-25 14:48:54'),(4,'PDC - RF Transaction Authorizor','ACTIVE','Madhawa_4799','2016-04-25 14:49:21','Madhawa_4799','2016-04-25 14:49:21'),(5,'PDC - RF Bussiness User','ACTIVE','Madhawa_4799','2016-04-25 14:49:21','Madhawa_4799','2016-04-25 14:49:21');
/*!40000 ALTER TABLE `ndb_user_levels` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-01 10:43:47
