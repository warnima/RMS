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
-- Table structure for table `ndb_user_forms`
--

DROP TABLE IF EXISTS `ndb_user_forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ndb_user_forms` (
  `idndb_user_forms` int(11) NOT NULL AUTO_INCREMENT,
  `ndb_form_name` varchar(245) DEFAULT NULL,
  `ndb_form_path` text,
  `ndb_user_system_group` varchar(45) DEFAULT NULL,
  `ndb_form_main_menu_id` varchar(245) DEFAULT NULL,
  `ndb_form_oder_by` varchar(245) DEFAULT NULL,
  `ndb_form_status` varchar(245) DEFAULT NULL,
  `ndb_form_creat_by` varchar(245) DEFAULT NULL,
  `ndb_form_creat_date` datetime DEFAULT NULL,
  `ndb_form_mod_by` varchar(245) DEFAULT NULL,
  `ndb_form_mod_date` datetime DEFAULT NULL,
  PRIMARY KEY (`idndb_user_forms`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ndb_user_forms`
--

LOCK TABLES `ndb_user_forms` WRITE;
/*!40000 ALTER TABLE `ndb_user_forms` DISABLE KEYS */;
INSERT INTO `ndb_user_forms` VALUES (39,'Master Files','RF - Master Files','RMSACTIVE','#','1','ACTIVE','Madhawa_4799','2016-05-19 10:30:25','Madhawa_4799','2016-05-19 10:30:25'),(40,'Master File Authorization','RF - Master File Authorization','RMSACTIVE','#','2','ACTIVE','Madhawa_4799','2016-05-19 10:32:01','Madhawa_4799','2016-05-19 10:32:01'),(41,'Transactions','RF - Transactions','RMSACTIVE','#','3','ACTIVE','Madhawa_4799','2016-05-19 10:32:26','Madhawa_4799','2016-05-19 10:32:26'),(42,'Transactions Authorization','RF - Transactions Authorization','RMSACTIVE','#','4','ACTIVE','Madhawa_4799','2016-05-19 10:32:49','Madhawa_4799','2016-05-19 10:32:49'),(43,'Process','RF - Process','RMSACTIVE','#','5','ACTIVE','Madhawa_4799','2016-05-19 10:33:05','Madhawa_4799','2016-05-19 10:33:05'),(44,'Repots And Charts','RF - Repots And Charts','RMSACTIVE','#','6','ACTIVE','Madhawa_4799','2016-05-19 10:33:26','Madhawa_4799','2016-05-19 10:33:26'),(45,'Bank File','pages/MasterFiles/ndb_bank.jsp','RMSACTIVE','39','1','ACTIVE','Madhawa_4799','2016-05-19 10:39:45','Madhawa_4799','2016-05-19 10:39:45'),(46,'Branch File','pages/MasterFiles/ndb_branch_file.jsp','RMSACTIVE','39','2','ACTIVE','Madhawa_4799','2016-05-19 10:40:04','Madhawa_4799','2016-05-19 10:40:04'),(47,'Industry File','pages/MasterFiles/ndb_industr_file.jsp','RMSACTIVE','39','3','ACTIVE','Madhawa_4799','2016-05-19 10:40:22','Madhawa_4799','2016-05-19 10:40:22'),(48,'Geographical Market File','pages/MasterFiles/ndb_geogr_file.jsp','RMSACTIVE','39','4','ACTIVE','Madhawa_4799','2016-05-19 10:40:43','Madhawa_4799','2016-05-19 10:40:43'),(49,'Manual Holiday Marker','pages/MasterFiles/ndb_holiday_marker.jsp','RMSACTIVE','39','5','ACTIVE','Madhawa_4799','2016-05-19 10:41:09','Madhawa_4799','2016-05-19 10:41:09'),(50,'Holiday Marker File Upload','pages/MasterFiles/ndb_holiday_file_upload.jsp','RMSACTIVE','39','6','ACTIVE','Madhawa_4799','2016-05-19 10:41:28','Madhawa_4799','2016-05-19 10:41:28'),(51,'Define Customer','pages/MasterFiles/ndb_customer_master.jsp','RMSACTIVE','39','7','ACTIVE','Madhawa_4799','2016-05-19 10:41:50','Madhawa_4799','2016-05-19 10:41:50'),(52,'Customer Product Mapping','pages/MasterFiles/ndb_prorelationship_setup.jsp','RMSACTIVE','39','8','ACTIVE','Madhawa_4799','2016-05-19 10:42:23','Madhawa_4799','2016-05-19 10:42:23'),(53,'Relationship Establishment','pages/MasterFiles/ndb_relationshipestab_setup.jsp','RMSACTIVE','39','9','ACTIVE','Madhawa_4799','2016-05-19 10:42:49','Madhawa_4799','2016-05-19 10:42:49'),(54,'Bank File Authorization','pages/MasterFiles/ndb_bank_authorization.jsp','RMSACTIVE','40','1','ACTIVE','Madhawa_4799','2016-05-19 10:43:10','Madhawa_4799','2016-05-19 10:43:10'),(55,'Branch File Authorization','pages/MasterFiles/ndb_branch_file_authorization.jsp','RMSACTIVE','40','2','ACTIVE','Madhawa_4799','2016-05-19 10:43:30','Madhawa_4799','2016-05-19 10:43:30'),(56,'Manual Holiday Marker Authorization','pages/MasterFiles/ndb_holiday_marker_authorization.jsp','RMSACTIVE','40','3','ACTIVE','Madhawa_4799','2016-05-19 10:43:54','Madhawa_4799','2016-05-19 10:43:54'),(57,'Define Customer Authorization','pages/MasterFiles/ndb_customer_master_Authorization.jsp','RMSACTIVE','40','4','ACTIVE','Madhawa_4799','2016-05-19 10:44:14','Madhawa_4799','2016-05-19 10:44:14'),(58,'Customer Product Mapping Authorization','pages/MasterFiles/ndb_prorelationship_setup_authorization.jsp','RMSACTIVE','40','5','ACTIVE','Madhawa_4799','2016-05-19 10:44:36','Madhawa_4799','2016-05-19 10:44:36'),(59,'Relationship Establishment Authorization','pages/MasterFiles/ndb_relationshipestab_setup_Authorization.jsp','RMSACTIVE','40','6','ACTIVE','Madhawa_4799','2016-05-19 10:44:58','Madhawa_4799','2016-05-19 10:44:58'),(60,'Manual Entry RF PDC','pages/pdc_txns/ndb_pdc_rf_manual.jsp','RMSACTIVE','41','1','ACTIVE','Madhawa_4799','2016-05-19 10:45:23','Madhawa_4799','2016-05-19 10:45:23'),(61,'Manual Entry CW PDC','pages/pdc_txns/ndb_pdc_cw_manual.jsp','RMSACTIVE','41','2','ACTIVE','Madhawa_4799','2016-05-19 10:45:42','Madhawa_4799','2016-05-19 10:45:42'),(62,'PDC Bulk Upload','pages/pdc_txns/ndb_pdc_fileupld.jsp','RMSACTIVE','41','3','ACTIVE','Madhawa_4799','2016-05-19 10:46:05','Madhawa_4799','2016-05-19 10:46:05'),(63,'PDC CW To RF Conversion','pages/pdc_txns/ndb_pdc_cw_rf_conversion.jsp','RMSACTIVE','41','4','ACTIVE','Madhawa_4799','2016-05-19 10:46:26','Madhawa_4799','2016-05-19 10:46:26'),(64,'Manual Entry RF PDC Authorization','pages/pdc_txns/ndb_pdc_rf_manual_authorization.jsp','RMSACTIVE','42','1','ACTIVE','Madhawa_4799','2016-05-19 10:46:50','Madhawa_4799','2016-05-19 10:46:50'),(65,'Manual Entry CW PDC Authorization','pages/pdc_txns/ndb_pdc_cw_manual_authorization.jsp','RMSACTIVE','42','2','ACTIVE','Madhawa_4799','2016-05-19 10:47:10','Madhawa_4799','2016-05-19 10:47:10'),(66,'Additional Day File Upload','pages/process/ndb_pdc_additionalupld.jsp','RMSACTIVE','43','1','ACTIVE','Madhawa_4799','2016-05-19 10:47:48','Madhawa_4799','2016-05-19 10:47:48'),(67,'Return Upload','pages/process/ndb_pdc_retupld.jsp','RMSACTIVE','43','2','ACTIVE','Madhawa_4799','2016-05-19 10:48:07','Madhawa_4799','2016-05-19 10:48:07'),(68,'Return / Liquidation Upload','pages/process/ndb_pdc_retleq.jsp','RMSACTIVE','43','3','ACTIVE','Madhawa_4799','2016-05-19 10:48:28','Madhawa_4799','2016-05-19 10:48:28'),(69,'Account Generation','pages/process/ndb_pdc_acc_gen.jsp','RMSACTIVE','43','4','ACTIVE','Madhawa_4799','2016-05-19 10:48:57','Madhawa_4799','2016-05-19 10:48:57'),(70,'Day End Process','pages/process/ndb_pdc_dayend.jsp','RMSACTIVE','43','5','ACTIVE','Madhawa_4799','2016-05-19 10:49:19','Madhawa_4799','2016-05-19 10:49:19'),(71,'PDC CW Received Cheques','pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp','RMSACTIVE','44','1','ACTIVE','Madhawa_4799','2016-05-19 10:49:42','Madhawa_4799','2016-05-19 10:49:42'),(72,'PDC CW Received Cheques Seller Wise','pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp','RMSACTIVE','44','2','ACTIVE','Madhawa_4799','2016-05-19 10:50:01','Madhawa_4799','2016-05-19 10:50:01'),(73,'PDC CW Value Date Extention','pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp','RMSACTIVE','44','3','ACTIVE','Madhawa_4799','2016-05-19 10:50:21','Madhawa_4799','2016-05-19 10:50:21'),(74,'PDC CW Seller Wise Buyer Facility','pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp','RMSACTIVE','44','4','ACTIVE','Madhawa_4799','2016-05-19 10:50:39','Madhawa_4799','2016-05-19 10:50:39'),(75,'PDC CW Seller Wise Facility','pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp','RMSACTIVE','44','5','ACTIVE','Madhawa_4799','2016-05-19 10:51:05','Madhawa_4799','2016-05-19 10:51:05'),(76,'PDC CW Cheque Status','pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp','RMSACTIVE','44','6','ACTIVE','Madhawa_4799','2016-05-19 10:51:27','Madhawa_4799','2016-05-19 10:51:27'),(77,'PDC RF Received Cheques','pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp','RMSACTIVE','44','7','ACTIVE','Madhawa_4799','2016-05-19 10:51:54','Madhawa_4799','2016-05-19 10:51:54'),(78,'PDC RF Received Cheques Seller Wise','pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp','RMSACTIVE','44','8','ACTIVE','Madhawa_4799','2016-05-19 10:52:31','Madhawa_4799','2016-05-19 10:52:31'),(79,'DC RF Value Date Extention','pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp','RMSACTIVE','44','9','ACTIVE','Madhawa_4799','2016-05-19 10:52:51','Madhawa_4799','2016-05-19 10:52:51'),(80,'PDC RF Seller Wise Buyer Facility','pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp','RMSACTIVE','44','10','ACTIVE','Madhawa_4799','2016-05-19 10:53:10','Madhawa_4799','2016-05-19 10:53:10'),(81,'PDC RF Seller Wise Facility','pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp','RMSACTIVE','44','11','ACTIVE','Madhawa_4799','2016-05-19 10:53:30','Madhawa_4799','2016-05-19 10:53:30'),(82,'PDC RF Cheque Status','pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp','RMSACTIVE','44','12','ACTIVE','Madhawa_4799','2016-05-19 10:53:53','Madhawa_4799','2016-05-19 10:53:53'),(83,'Today Booked Cheques SLIPS Download','pages/RMSreports/ndb_today_booked_cheque_sending.jsp','RMSACTIVE','44','13','ACTIVE','Madhawa_4799','2016-05-19 10:54:13','Madhawa_4799','2016-05-19 10:54:13'),(84,'Today Clearing Cheques','pages/RMSreports/ndb_cheque_sending.jsp','RMSACTIVE','44','14','ACTIVE','Madhawa_4799','2016-05-19 10:54:33','Madhawa_4799','2016-05-19 10:54:33');
/*!40000 ALTER TABLE `ndb_user_forms` ENABLE KEYS */;
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
