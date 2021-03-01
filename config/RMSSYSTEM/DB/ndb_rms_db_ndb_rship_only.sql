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
-- Table structure for table `ndb_rship_only`
--

DROP TABLE IF EXISTS `ndb_rship_only`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ndb_rship_only` (
  `idndb_rship_only` int(11) NOT NULL AUTO_INCREMENT,
  `idndb_customer_define` varchar(45) DEFAULT NULL,
  `idndb_cust_prod_map` varchar(45) DEFAULT NULL,
  `rship_only_faclity_limit` double DEFAULT NULL,
  `rship_only_commision_crg` varchar(45) DEFAULT NULL,
  `ro_tran_base_falt_fee` double DEFAULT NULL,
  `ro_tran_base_from_tran` double DEFAULT NULL,
  `ro_fixed_rate_amount` double DEFAULT NULL,
  `ro_fixed_frequency` varchar(45) DEFAULT NULL,
  `rship_only_erly_wdr_chg` double DEFAULT NULL,
  `rship_only_vale_dte_extr_chg` double DEFAULT NULL,
  `rship_only_erly_stlemnt_chg` double DEFAULT NULL,
  `rship_only_creat_by` varchar(45) DEFAULT NULL,
  `rship_only_creat_date` datetime DEFAULT NULL,
  `rship_only_mod_by` varchar(45) DEFAULT NULL,
  `rship_only_mod_date` datetime DEFAULT NULL,
  `rship_only_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idndb_rship_only`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ndb_rship_only`
--

LOCK TABLES `ndb_rship_only` WRITE;
/*!40000 ALTER TABLE `ndb_rship_only` DISABLE KEYS */;
/*!40000 ALTER TABLE `ndb_rship_only` ENABLE KEYS */;
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
