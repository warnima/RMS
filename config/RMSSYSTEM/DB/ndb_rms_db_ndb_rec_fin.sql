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
-- Table structure for table `ndb_rec_fin`
--

DROP TABLE IF EXISTS `ndb_rec_fin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ndb_rec_fin` (
  `idndb_rec_fin` int(11) NOT NULL AUTO_INCREMENT,
  `idndb_customer_define` varchar(45) DEFAULT NULL,
  `idndb_cust_prod_map` varchar(45) DEFAULT NULL,
  `rec_finance_curr` varchar(45) DEFAULT NULL,
  `rec_finance_limit` double DEFAULT NULL,
  `rec_finance_financing` double DEFAULT NULL,
  `rec_finance_inerest_rate` double DEFAULT NULL,
  `rec_finance_acc_num` varchar(45) DEFAULT NULL,
  `rec_finance_cr_dsc_proc_acc_num` varchar(45) DEFAULT NULL,
  `rec_finance_current_ac` varchar(45) DEFAULT NULL,
  `rec_finance_margin_ac` varchar(45) DEFAULT NULL,
  `rec_finance_margin` double DEFAULT NULL,
  `cust_credit_rate` varchar(45) DEFAULT NULL,
  `rec_finance_bulk_credit` varchar(45) DEFAULT NULL,
  `rec_finance_tenor` varchar(45) DEFAULT NULL,
  `rec_finance_erly_wdr_chg` double DEFAULT NULL,
  `rec_finance_vale_dte_extr_chg` double DEFAULT NULL,
  `rec_finance_erly_stlemnt_chg` double DEFAULT NULL,
  `rec_finance_creat_by` varchar(45) DEFAULT NULL,
  `rec_finance_creat_date` datetime DEFAULT NULL,
  `rec_finance_mod_by` varchar(45) DEFAULT NULL,
  `rec_finance_mod_date` datetime DEFAULT NULL,
  `rec_finance_status` varchar(45) DEFAULT NULL,
  `rec_finance_Outstanding` double DEFAULT NULL,
  `rec_finance_balance_transfer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idndb_rec_fin`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ndb_rec_fin`
--

LOCK TABLES `ndb_rec_fin` WRITE;
/*!40000 ALTER TABLE `ndb_rec_fin` DISABLE KEYS */;
INSERT INTO `ndb_rec_fin` VALUES (1,'1','1','LKR',10000000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','45',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-02-28 15:30:16','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(2,'2','2','LKR',0,50,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-06-30 14:55:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(3,'3','3','LKR',50000000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2020-11-13 15:42:09','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(4,'4','4','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2019-11-08 15:27:03','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(5,'5','5','LKR',0,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-08-29 10:24:20','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(6,'6','6','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2020-01-23 14:21:59','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(7,'7','7','LKR',0,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-01-04 13:27:57','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(8,'8','8','LKR',0,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-06-30 15:07:19','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(9,'9','9','LKR',0,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','varuni_2506','2018-04-12 16:24:29','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(10,'10','10','LKR',0,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2019-12-13 09:25:51','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(11,'11','11','LKR',20000000,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','SYSTEM_INPUTTER','2016-07-22 18:00:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(12,'12','12','LKR',220000000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-07-21 12:46:52','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(13,'13','13','LKR',0,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','30',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-01-04 13:28:34','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(14,'14','14','LKR',0,75,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2017-12-20 12:45:59','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(15,'15','15','LKR',7500000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','SYSTEM_INPUTTER','2016-07-22 18:00:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(16,'16','16','LKR',4000000,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2018-09-19 16:41:41','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(17,'17','17','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-03-05 14:56:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(18,'18','18','LKR',30000000,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-07-23 13:11:03','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(19,'19','19','LKR',32690183,100,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2020-05-12 06:16:36','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(20,'20','20','LKR',0,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','45',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-06-30 15:09:04','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(21,'21','21','LKR',0,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Goshika_7528','2020-03-11 09:01:55','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(22,'22','22','LKR',0,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','45',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2019-06-07 12:31:27','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(23,'23','23','LKR',0,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sewwandi_6875','2020-12-22 10:22:04','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(24,'24','24','LKR',200000000,85,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2020-10-19 11:13:29','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(25,'25','25','LKR',70000000,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-07-16 15:29:23','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(26,'26','26','LKR',5000000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','SYSTEM_INPUTTER','2016-07-22 18:00:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(27,'27','27','LKR',20000000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','SYSTEM_INPUTTER','2016-07-22 18:00:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(28,'28','28','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2018-07-06 10:51:52','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(29,'29','29','LKR',0,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sewwandi_6875','2020-12-22 11:57:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(30,'30','30','LKR',0,75,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-06-30 15:13:44','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(31,'31','31','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2019-06-06 09:37:28','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(32,'32','32','LKR',60000000,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2020-09-30 14:15:19','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(33,'33','33','LKR',0,75,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2017-12-19 12:05:37','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(34,'34','34','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2018-04-12 16:22:11','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(35,'35','35','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-12-11 11:55:01','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(36,'36','36','LKR',20000000,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2020-11-17 14:41:43','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(37,'37','37','LKR',15000000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','30',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','SYSTEM_INPUTTER','2016-07-22 18:00:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(38,'38','38','LKR',0,85,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2019-06-07 12:32:30','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(39,'39','39','LKR',0,85,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2019-06-07 12:32:01','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(40,'40','40','LKR',50000000,80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2020-12-22 14:42:07','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(41,'41','41','LKR',0,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','DANUSHKA_4755','2018-08-21 11:33:39','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(42,'42','42','LKR',0,75,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-06-30 14:55:55','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(43,'43','43','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2019-08-29 07:14:47','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(44,'44','44','LKR',13000000,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','chamindu_4274','2018-03-12 15:48:11','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(45,'45','45','LKR',0,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-05-28 13:17:24','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(46,'46','46','LKR',0,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2020-06-30 15:15:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(47,'47','47','LKR',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT ACTIVE','0',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-09-04 10:41:06','ACTIVE',NULL,'BALANCE TRANSFER DEACTIVE'),(48,'48','48','LKR',0,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-01-04 15:43:51','ACTIVE',NULL,'BALANCE TRANSFER DEACTIVE'),(49,'49','49','LKR',0,60,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sathya_5003','2019-11-13 16:16:49','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(50,'50','50','LKR',15000000,75,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','SYSTEM_INPUTTER','2016-07-22 18:00:00','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(51,'51','51','LKR',0,75,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','Sewwandi_6875','2020-12-22 12:01:39','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(52,'52','52','LKR',0,85,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'SYSTEM_INPUTTER','2016-07-22 18:00:00','dulan_5289','2018-02-06 09:14:54','ACTIVE',NULL,'BALANCE TRANSFER ACTIVE'),(53,'1755','1755','LKR',1500000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','30',0,0,0,'nilushani_2604','2016-08-02 14:46:47','nilushani_2604','2016-08-02 14:46:47','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(54,'1757','1757','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'CHATHURIKA_2773','2016-08-25 12:05:00','Dineesha_3610','2017-09-06 09:34:44','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(55,'1764','1764','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'CHATHURIKA_2773','2016-08-25 14:49:43','dulan_5289','2017-12-15 10:34:05','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(56,'1766','1766','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'CHATHURIKA_2773','2016-08-29 13:12:07','DANUSHKA_4755','2018-07-13 16:55:03','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(57,'1776','1777','LKR',20000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'CHATHURIKA_2773','2016-09-22 09:19:47','Sathya_5003','2020-07-16 15:28:49','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(58,'1778','1779','LKR',15000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'CHATHURIKA_2773','2016-09-22 12:10:24','DANUSHKA_4755','2020-12-22 14:39:52','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(59,'1784','1785','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'nilushani_2604','2016-10-06 14:41:31','dulan_5289','2019-01-02 10:29:59','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(60,'1808','1816','LKR',0,60,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'nilushani_2604','2016-12-29 15:53:55','dulan_5289','2017-12-15 10:16:49','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(61,'1831','1835','LKR',0,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'nilushani_2604','2017-01-17 14:10:56','Goshika_7528','2020-10-28 15:12:44','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(62,'1840','1842','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',100,100,100,'Dineesha_3610','2017-02-13 08:57:24','Sathya_5003','2020-06-30 15:14:33','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(63,'1842','1846','LKR',40000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'Dineesha_3610','2017-02-15 14:56:59','Sathya_5003','2020-09-04 15:48:07','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(64,'1852','1854','LKR',0,85,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'nilushani_2604','2017-03-03 15:28:05','DANUSHKA_4755','2020-01-27 10:09:36','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(65,'1863','1865','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'Dineesha_3610','2017-03-27 15:07:57','dulan_5289','2019-03-07 11:26:36','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(66,'1886','1888','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'Dineesha_3610','2017-04-07 15:26:30','Sathya_5003','2019-07-04 10:52:53','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(67,'1896','1898','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','30',0,0,0,'Dineesha_3610','2017-05-24 12:49:54','dulan_5289','2018-02-06 09:14:25','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(68,'1898','1900','LKR',0,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',0,0,0,'Dineesha_3610','2017-05-29 15:20:29','Sathya_5003','2020-06-30 15:06:42','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(69,'1902','1904','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'nilushani_2604','2017-06-07 10:54:13','Goshika_7528','2020-03-10 15:37:38','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(70,'1918','1919','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'nilushani_2604','2017-06-28 12:36:30','dulan_5289','2019-01-24 13:17:50','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(71,'1836','1838','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'Dineesha_3610','2017-08-15 11:07:51','Sathya_5003','2019-05-06 09:06:32','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(72,'2004','2014','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','',0,0,0,'Dineesha_3610','2017-08-17 14:27:52','DANUSHKA_4755','2018-11-16 15:56:56','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(73,'2011','2021','LKR',7500000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'Dineesha_3610','2017-08-24 10:38:31','Sewwandi_6875','2020-07-27 08:40:47','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(74,'2034','2044','LKR',2500000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'Dineesha_3610','2017-09-11 15:19:11','Sathya_5003','2020-06-30 13:09:50','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(75,'2049','2059','LKR',8000000,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','21',100,100,100,'Dineesha_3610','2017-09-29 15:36:08','Sathya_5003','2020-07-01 10:51:48','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(76,'2056','2066','LKR',23763032,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',100,100,100,'dulan_5289','2017-10-17 15:10:09','dulan_5289','2020-05-12 06:25:15','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(77,'2068','2078','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'dulan_5289','2017-11-01 16:08:32','Sewwandi_6875','2020-12-22 12:02:03','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(78,'2088','2096','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'dulan_5289','2017-11-27 12:57:04','Sathya_5003','2019-07-03 16:14:49','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(79,'2083','2101','LKR',20315454,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',0,0,0,'dulan_5289','2017-11-30 11:07:12','dulan_5289','2020-05-12 08:55:59','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(80,'2097','2108','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'dulan_5289','2017-12-11 08:35:50','DANUSHKA_4755','2018-08-21 10:31:40','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(81,'2139','2150','LKR',10000000,90,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2018-01-16 10:08:54','DANUSHKA_4755','2019-11-08 12:21:47','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(82,'1730','1730','LKR',50000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2018-02-02 11:49:37','DANUSHKA_4755','2020-12-08 09:42:40','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(83,'1726','1726','LKR',50000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2018-02-02 11:50:50','Sewwandi_6875','2020-12-22 10:08:48','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(84,'2171','2183','LKR',20000000,40,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'dulan_5289','2018-02-09 15:53:15','DANUSHKA_4755','2019-08-13 11:50:38','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(85,'2173','2184','LKR',0,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','30',0,0,0,'dulan_5289','2018-02-15 09:34:54','Goshika_7528','2020-03-10 14:52:49','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(86,'2186','2195','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'DANUSHKA_4755','2018-04-03 10:02:45','dulan_5289','2019-03-25 11:53:29','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(87,'2289','2299','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'dulan_5289','2018-05-30 13:03:03','dulan_5289','2019-03-14 14:15:19','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(88,'2298','2309','LKR',13000000,90,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2018-06-12 14:08:07','Sathya_5003','2020-07-01 10:51:23','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(89,'1846','1848','LKR',0,85,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',100,100,100,'dulan_5289','2018-07-18 09:51:32','Sewwandi_6875','2020-12-22 12:39:25','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(90,'2334','2345','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','',0,0,0,'dulan_5289','2018-08-07 11:42:46','DANUSHKA_4755','2019-09-30 09:37:28','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(91,'2336','2347','LKR',0,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'dulan_5289','2018-08-09 09:46:59','DANUSHKA_4755','2018-10-25 16:36:58','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(92,'2343','2354','LKR',0,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',100,100,100,'dulan_5289','2018-08-17 13:15:25','Sewwandi_6875','2020-12-22 11:57:46','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(93,'2392','2403','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'DANUSHKA_4755','2018-09-19 10:19:34','Sewwandi_6875','2020-12-22 12:01:11','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(94,'2396','2407','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'DANUSHKA_4755','2018-09-20 12:37:09','Goshika_7528','2020-09-22 10:47:02','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(95,'2398','2409','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'DANUSHKA_4755','2018-09-27 15:15:28','Sewwandi_6875','2020-12-22 12:00:38','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(96,'2417','2428','LKR',5000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','45',100,100,100,'DANUSHKA_4755','2018-10-15 15:33:41','DANUSHKA_4755','2018-10-15 15:33:41','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(97,'2419','2430','LKR',15000000,90,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'DANUSHKA_4755','2018-10-16 12:21:42','Sathya_5003','2019-10-04 15:46:36','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(98,'2441','2454','LKR',4200000,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','70',100,100,100,'DANUSHKA_4755','2018-10-31 10:19:42','Sewwandi_6875','2020-12-22 10:25:45','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(99,'2445','2456','LKR',1226580,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2018-11-01 16:17:00','dulan_5289','2020-05-12 06:36:31','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(100,'2196','2207','LKR',12500000,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'dulan_5289','2018-11-15 11:25:30','Sathya_5003','2020-07-16 15:29:53','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(101,'2526','2533','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'dulan_5289','2018-11-27 12:25:41','Sathya_5003','2020-07-06 15:57:09','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(102,'2534','2545','LKR',0,90,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'DANUSHKA_4755','2018-11-28 15:01:18','Sathya_5003','2020-06-30 15:14:11','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(103,'2188','2199','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'dulan_5289','2018-12-11 11:45:41','Sewwandi_6875','2020-12-22 10:23:52','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(104,'2558','2569','LKR',500000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'dulan_5289','2019-01-03 12:08:04','dulan_5289','2020-05-12 06:40:12','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(105,'2565','2576','LKR',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','0',0,0,0,'dulan_5289','2019-01-04 13:55:47','Sathya_5003','2019-09-18 15:32:40','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(106,'2618','2629','LKR',6000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2019-01-29 13:16:04','Sathya_5003','2020-07-01 10:50:51','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(107,'2643','2654','LKR',0,70,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'DANUSHKA_4755','2019-01-31 16:24:27','Sathya_5003','2020-06-30 14:55:29','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(108,'2706','2728','LKR',20000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'dulan_5289','2019-03-15 09:59:42','Sathya_5003','2020-07-01 10:52:17','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(109,'2721','2731','LKR',15000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2019-03-28 10:10:19','dulan_5289','2019-03-28 10:10:19','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(110,'2723','2733','LKR',1000000,70,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'dulan_5289','2019-04-01 11:24:39','dulan_5289','2020-05-12 08:58:24','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(111,'2890','2897','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','45',0,0,0,'Goshika_11072','2019-08-05 15:50:25','Sathya_5003','2020-09-29 14:56:36','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(112,'2895','2902','LKR',5000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'Goshika_11072','2019-08-19 16:15:55','Sewwandi_6875','2020-12-22 10:25:00','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(113,'2902','2909','LKR',0,70,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','30',100,100,100,'Sathya_5003','2019-09-12 16:49:42','Sathya_5003','2020-07-02 11:25:53','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(114,'2916','2923','LKR',10000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',100,100,100,'Sathya_5003','2019-11-04 15:28:41','Sathya_5003','2020-07-01 10:49:35','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(115,'2920','2927','LKR',20000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'DANUSHKA_4755','2019-11-08 17:32:04','DANUSHKA_4755','2020-12-22 10:38:35','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(116,'2914','2921','LKR',50000000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'Sathya_5003','2019-11-29 12:57:45','Sathya_5003','2020-07-01 10:53:02','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(117,'3009','3016','LKR',5996651,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','120',100,100,100,'DANUSHKA_4755','2019-12-20 14:57:14','Sewwandi_6875','2020-12-22 12:00:03','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(118,'3032','3039','LKR',25000000,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'Sathya_5003','2020-01-09 15:42:09','Sewwandi_6875','2020-08-26 10:37:51','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(119,'3034','3041','LKR',5000000,90,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','60',100,100,100,'Sewwandi_6875','2020-01-13 10:09:04','Sewwandi_6875','2020-01-13 10:09:04','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(120,'2980','2987','LKR',0,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'dulan_5289','2020-02-12 12:44:27','Sathya_5003','2020-12-28 11:43:19','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(121,'3086','3095','LKR',0,75,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',0,0,0,'Sewwandi_6875','2020-03-11 09:47:20','Sewwandi_6875','2020-10-29 14:30:17','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(122,'3128','3136','LKR',7500000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'Sathya_5003','2020-08-17 09:15:51','Sathya_5003','2020-08-17 09:15:51','ACTIVE',0,'BALANCE TRANSFER ACTIVE'),(123,'3126','3134','LKR',2500000,80,0,NULL,NULL,NULL,NULL,NULL,NULL,'BULK CREDIT DEACTIVE','90',100,100,100,'Goshika_7528','2020-09-21 15:36:58','Goshika_7528','2020-09-21 15:36:58','ACTIVE',0,'BALANCE TRANSFER ACTIVE');
/*!40000 ALTER TABLE `ndb_rec_fin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-01 10:43:50