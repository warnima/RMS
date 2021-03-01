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
-- Table structure for table `ndb_master_menu`
--

DROP TABLE IF EXISTS `ndb_master_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ndb_master_menu` (
  `idndb_master_menu` int(11) NOT NULL AUTO_INCREMENT,
  `master_menu_id` varchar(45) DEFAULT NULL,
  `master_menu_description` varchar(45) DEFAULT NULL,
  `master_menu_main_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idndb_master_menu`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ndb_master_menu`
--

LOCK TABLES `ndb_master_menu` WRITE;
/*!40000 ALTER TABLE `ndb_master_menu` DISABLE KEYS */;
INSERT INTO `ndb_master_menu` VALUES (1,'Master_Files','Master Files','Master_Files'),(2,'Transactions','Transactions','Transactions'),(3,'Process','Process','Process'),(4,'Repots_And_Charts','Repots And Charts','Repots_And_Charts'),(5,'Bank_File','Bank File','Master_Files'),(6,'Branch_File','Branch File','Master_Files'),(7,'Industry_File','Industry File','Master_Files'),(8,'Geo_Market_File','Geographical Market File','Master_Files'),(9,'Define_Customer','Define Customer','Master_Files'),(10,'Cust_Prod_Map','Customer Product Mapping','Master_Files'),(11,'Relship_Estab','Relationship Establishment','Master_Files');
/*!40000 ALTER TABLE `ndb_master_menu` ENABLE KEYS */;
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
