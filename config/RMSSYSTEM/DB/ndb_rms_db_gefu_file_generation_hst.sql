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
-- Table structure for table `gefu_file_generation_hst`
--

DROP TABLE IF EXISTS `gefu_file_generation_hst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gefu_file_generation_hst` (
  `idgefu_file_generation` int(11) DEFAULT NULL,
  `idndb_pdc_txn_master` varchar(45) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `currency` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `narration` varchar(245) DEFAULT NULL,
  `credit_debit` varchar(45) DEFAULT NULL,
  `profit_centre` varchar(45) DEFAULT NULL,
  `DAO` varchar(45) DEFAULT NULL,
  `c_amount` double DEFAULT NULL,
  `d_amount` double DEFAULT NULL,
  `gefu_creation_status` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `creat_by` varchar(45) DEFAULT NULL,
  `creat_date` datetime DEFAULT NULL,
  `mod_by` varchar(45) DEFAULT NULL,
  `mod_date` datetime DEFAULT NULL,
  `system_date` varchar(45) DEFAULT NULL,
  `cw_fixed_frequency` varchar(45) DEFAULT NULL,
  `gefu_type` varchar(45) DEFAULT NULL,
  `bulk_credit` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gefu_file_generation_hst`
--

LOCK TABLES `gefu_file_generation_hst` WRITE;
/*!40000 ALTER TABLE `gefu_file_generation_hst` DISABLE KEYS */;
/*!40000 ALTER TABLE `gefu_file_generation_hst` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-01 10:43:35
