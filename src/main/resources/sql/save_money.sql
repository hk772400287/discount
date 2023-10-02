/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 8.0.32 : Database - save_money
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`save_money` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `save_money`;

/*Table structure for table `discount` */

DROP TABLE IF EXISTS `discount`;

CREATE TABLE `discount` (
  `id` bigint NOT NULL,
  `description` varchar(500) NOT NULL,
  `start` date NOT NULL,
  `end` date NOT NULL,
  `max_amount` decimal(10,2) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '1:deleted, 0:not deleted',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `discount_store` */

DROP TABLE IF EXISTS `discount_store`;

CREATE TABLE `discount_store` (
  `id` bigint NOT NULL,
  `discount_id` bigint NOT NULL,
  `store_id` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '1:deleted, 0:not deleted',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `favourite` */

DROP TABLE IF EXISTS `favourite`;

CREATE TABLE `favourite` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `store_id` bigint DEFAULT NULL,
  `discount_id` bigint DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `store_id` (`store_id`),
  KEY `discount_id` (`discount_id`),
  CONSTRAINT `favourite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `favourite_ibfk_2` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
  CONSTRAINT `favourite_ibfk_3` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `store` */

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `id` bigint NOT NULL,
  `name` varchar(50) NOT NULL,
  `icon` varchar(500) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '1:deleted, 0:not deleted',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `is_admin` int NOT NULL DEFAULT '0' COMMENT '0:no, 1:yes',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `user_discount_balance` */

DROP TABLE IF EXISTS `user_discount_balance`;

CREATE TABLE `user_discount_balance` (
  `user_id` bigint NOT NULL,
  `discount_id` bigint NOT NULL,
  `balance_amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`discount_id`),
  KEY `discount_id` (`discount_id`),
  CONSTRAINT `user_discount_balance_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_discount_balance_ibfk_2` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `user_discount_spending` */

DROP TABLE IF EXISTS `user_discount_spending`;

CREATE TABLE `user_discount_spending` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `discount_id` bigint NOT NULL,
  `store_id` bigint NOT NULL,
  `store_name` varchar(50) NOT NULL,
  `date` date DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `discount_id` (`discount_id`),
  KEY `store_id` (`store_id`),
  CONSTRAINT `user_discount_spending_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_discount_spending_ibfk_2` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`),
  CONSTRAINT `user_discount_spending_ibfk_3` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
