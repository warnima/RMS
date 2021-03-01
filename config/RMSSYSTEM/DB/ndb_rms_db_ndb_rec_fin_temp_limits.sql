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
-- Table structure for table `ndb_rec_fin_temp_limits`
--

DROP TABLE IF EXISTS `ndb_rec_fin_temp_limits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ndb_rec_fin_temp_limits` (
  `idndb_rec_fin_temp_limits` int(11) NOT NULL AUTO_INCREMENT,
  `ndb_rec_fin_temp_limit_record_no` int(11) DEFAULT NULL,
  `ndb_rec_fin_temp_value` double DEFAULT NULL,
  `ndb_rec_fin_temp_exp_date` date DEFAULT NULL,
  `idndb_rec_fin` int(11) DEFAULT NULL,
  PRIMARY KEY (`idndb_rec_fin_temp_limits`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ndb_rec_fin_temp_limits`
--

LOCK TABLES `ndb_rec_fin_temp_limits` WRITE;
/*!40000 ALTER TABLE `ndb_rec_fin_temp_limits` DISABLE KEYS */;
INSERT INTO `ndb_rec_fin_temp_limits` VALUES (193,0,9000000,'2019-01-20',78),(197,0,2000000,'2019-04-28',31),(210,0,12800000,'2019-12-31',46),(211,1,35000000,'2019-12-31',46),(216,0,20000000,'2020-05-10',12),(218,1,5000000,'2020-06-20',63),(219,1,10000000,'2020-07-31',63),(220,0,20000000,'2021-03-31',32);
/*!40000 ALTER TABLE `ndb_rec_fin_temp_limits` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-01 10:43:48
