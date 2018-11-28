/*
Navicat MySQL Data Transfer

Source Server         : aliyun
Source Server Version : 50722
Source Host           : 120.79.244.48:3306
Source Database       : demoauth

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-11-29 00:16:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ccode
-- ----------------------------
DROP TABLE IF EXISTS `ccode`;
CREATE TABLE `ccode` (
  `cacode` varchar(10) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `exptime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `mobilephone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
