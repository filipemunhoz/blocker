DROP DATABASE IF EXISTS `blocker`;
CREATE DATABASE `blocker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;;
use blocker;
DROP TABLE IF EXISTS `ip`;
DROP TABLE IF EXISTS `ipv6`;
CREATE TABLE `ip` ( `id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` int NOT NULL, `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
insert into `ip` (address, origin) values ( '-1062797052', 'IpServiceT'); 
insert into `ip` (address, origin) values ( '-1062797037', 'IpServiceT');
insert into `ip` (address, origin) values ( '-1062797037', 'IpServiceT');
CREATE TABLE `ipv6` (`id` int NOT NULL AUTO_INCREMENT, `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, `address` varbinary(16) NOT NULL,  `origin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
insert into `ipv6` (address, origin) values ( INET6_ATON('fe80::a8c6:6cff:fe96:7fd1'), 'Ipv6Test')