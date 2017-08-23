/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50703
Source Host           : localhost:3306
Source Database       : appmonitor

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2014-09-25 18:04:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bmsuser
-- ----------------------------
DROP TABLE IF EXISTS `bmsuser`;
CREATE TABLE `bmsuser` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) NOT NULL,
  `UserPass` varchar(50) NOT NULL,
  `UserType` int(11) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Mobile` varchar(50) NOT NULL,
  `Phone` varchar(50) NOT NULL,
  `Description` varchar(250) NOT NULL,
  `Active` bit(1) NOT NULL,
  `LogonTimes` int(11) NOT NULL,
  `LogonIp` varchar(20) DEFAULT NULL,
  `LastLogonTime` datetime DEFAULT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(50) NOT NULL,
  `CreateUserID` int(11) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UserName` (`UserName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for bmsgroup
-- ----------------------------
DROP TABLE IF EXISTS `bmsgroup`;
CREATE TABLE `bmsgroup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GroupName` varchar(50) NOT NULL,
  `GroupType` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(50) NOT NULL,
  `CreateUserID` int(11) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `GroupName` (`GroupName`) USING BTREE,
  KEY `GroupType` (`GroupType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for bmsusergroup
-- ----------------------------
DROP TABLE IF EXISTS `bmsusergroup`;
CREATE TABLE `bmsusergroup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `GroupID` int(11) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(50) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UserGroup` (`UserID`,`GroupID`) USING BTREE,
  KEY `BMSUsergroup_ibfk_2` (`GroupID`),
  CONSTRAINT `BMSUsergroup_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `bmsuser` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `BMSUsergroup_ibfk_2` FOREIGN KEY (`GroupID`) REFERENCES `bmsgroup` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for bmsmenu
-- ----------------------------
DROP TABLE IF EXISTS `bmsmenu`;
CREATE TABLE `bmsmenu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MenuName` varchar(50) NOT NULL,
  `ParentID` int(11) NOT NULL,
  `Url` varchar(250) NOT NULL,
  `Sort` int(11) NOT NULL,
  `MenuType` int(11) NOT NULL DEFAULT '1',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(50) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bmsauth
-- ----------------------------
DROP TABLE IF EXISTS `bmsauth`;
CREATE TABLE `bmsauth` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FunctionID` varchar(50) NOT NULL,
  `AuthName` varchar(50) NOT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `Status` bit(1) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(50) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AUTH_ID` (`ID`) USING BTREE,
  UNIQUE KEY `AUTH_FUNC_ID` (`FunctionID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for bmsauthmapping
-- ----------------------------
DROP TABLE IF EXISTS `bmsauthmapping`;
CREATE TABLE `bmsauthmapping` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MenuID` int(11) NOT NULL,
  `AuthID` int(11) NOT NULL,
  `Url` varchar(255) NOT NULL,
  `Status` bit(1) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(255) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AuthMapping` (`MenuID`,`AuthID`,`Url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bmsgroupauth
-- ----------------------------
DROP TABLE IF EXISTS `bmsgroupauth`;
CREATE TABLE `bmsgroupauth` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GroupID` int(11) NOT NULL,
  `AuthID` int(11) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(50) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `GroupAuth` (`GroupID`,`AuthID`) USING BTREE,
  KEY `AuthID` (`AuthID`),
  CONSTRAINT `bmsgroupauth_ibfk_1` FOREIGN KEY (`GroupID`) REFERENCES `bmsgroup` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `bmsgroupauth_ibfk_2` FOREIGN KEY (`AuthID`) REFERENCES `bmsauth` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bmsgroupmenu
-- ----------------------------
DROP TABLE IF EXISTS `bmsgroupmenu`;
CREATE TABLE `bmsgroupmenu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GroupID` int(11) NOT NULL,
  `MenuID` int(11) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(50) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `GroupMenu` (`GroupID`,`MenuID`) USING BTREE,
  KEY `BMSGroupmenu_ibfk_1` (`MenuID`),
  CONSTRAINT `BMSGroupmenu_ibfk_1` FOREIGN KEY (`MenuID`) REFERENCES `bmsmenu` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `BMSGroupmenu_ibfk_2` FOREIGN KEY (`GroupID`) REFERENCES `bmsgroup` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bmsmenuauth
-- ----------------------------
DROP TABLE IF EXISTS `bmsmenuauth`;
CREATE TABLE `bmsmenuauth` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MenuID` int(11) NOT NULL,
  `AuthID` int(11) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreateUser` varchar(255) NOT NULL,
  `UpdateTime` datetime NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MenuAuth` (`MenuID`,`AuthID`) USING BTREE,
  KEY `bmsmenuauth_ibfk_1` (`MenuID`),
  KEY `bmsmenuauth_ibfk_2` (`AuthID`),
  CONSTRAINT `bmsmenuauth_ibfk_1` FOREIGN KEY (`MenuID`) REFERENCES `bmsmenu` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `bmsmenuauth_ibfk_2` FOREIGN KEY (`AuthID`) REFERENCES `bmsauth` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bmsuser
-- ----------------------------
INSERT INTO `bmsuser` VALUES ('1', 'admin', '21232F297A57A5A743894A0E4A801FC3', '1', '14876534@qq.com', '13818668209', '021-11011011', '', '', '103', '127.0.0.1', '2014-09-22 15:43:17', '2014-08-27 15:42:14', 'admin', '1', '2014-08-27 15:42:14');


-- ----------------------------
-- Records of bmsgroup
-- ----------------------------
INSERT INTO `bmsgroup` VALUES ('1', 'admin', '', '管理员', '2014-08-27 15:42:14', 'admin', '1', '2014-09-10 15:39:18');
INSERT INTO `bmsgroup` VALUES ('2', 'automail', 'mail', '自动发送出错信息组', '2014-09-17 11:20:18', 'admin', '1', '2014-09-17 11:20:18');
INSERT INTO `bmsgroup` VALUES ('3', '邮件组1', 'mail', '发送监控邮件1', '2014-09-10 15:47:25', 'admin', '1', '2014-09-10 16:41:56');


-- ----------------------------
-- Records of bmsusergroup
-- ----------------------------
INSERT INTO `bmsusergroup` VALUES ('1', '1', '1', '2014-09-10 15:47:36', 'admin', '2014-09-10 15:47:36');
INSERT INTO `bmsusergroup` VALUES ('2', '1', '2', '2014-09-03 12:31:24', 'admin', '2014-09-03 12:31:24');
INSERT INTO `bmsusergroup` VALUES ('3', '1', '3', '2014-09-10 15:47:36', 'admin', '2014-09-10 15:47:36');


-- ----------------------------
-- Records of bmsmenu
-- ----------------------------
INSERT INTO `bmsmenu` VALUES ('1', '系统设置', '0', '', '1', '1', '2014-08-27 15:42:14', 'admin', '2014-08-27 15:42:14');
INSERT INTO `bmsmenu` VALUES ('2', '权限配置', '1', '/mgr/authset/', '1', '1', '2014-08-27 15:42:15', 'admin', '2014-08-27 15:42:15');
INSERT INTO `bmsmenu` VALUES ('3', '菜单管理', '1', '/mgr/menu/', '2', '1', '2014-08-27 15:42:15', 'admin', '2014-08-27 15:42:15');
INSERT INTO `bmsmenu` VALUES ('4', '用户管理', '1', '/mgr/user/', '3', '1', '2014-08-27 15:42:15', 'admin', '2014-08-27 15:42:15');
INSERT INTO `bmsmenu` VALUES ('5', '分组管理', '1', '/mgr/group/', '4', '1', '2014-08-27 15:42:15', 'admin', '2014-08-27 15:42:15');

-- ----------------------------
-- Records of bmsauth
-- ----------------------------
INSERT INTO `bmsauth` VALUES ('1', 'USER_VIEW', '用户查看', '', '', '2014-08-27 15:42:17', 'admin', '2014-08-27 15:42:17');
INSERT INTO `bmsauth` VALUES ('2', 'USER_ADD', '用户增加', '', '', '2014-08-27 15:42:17', 'admin', '2014-08-27 15:42:17');
INSERT INTO `bmsauth` VALUES ('3', 'USER_DELETE', '用户删除', '', '', '2014-08-27 15:42:17', 'admin', '2014-08-27 15:42:17');
INSERT INTO `bmsauth` VALUES ('4', 'USER_UPDATE', '用户更新', '', '', '2014-08-27 15:42:17', 'admin', '2014-08-27 15:42:17');
INSERT INTO `bmsauth` VALUES ('5', 'GROUP_VIEW', '分组查看', '', '', '2014-08-27 15:42:17', 'admin', '2014-08-27 15:42:17');
INSERT INTO `bmsauth` VALUES ('6', 'GROUP_ADD', '分组增加', '', '', '2014-08-27 15:42:18', 'admin', '2014-08-27 15:42:18');
INSERT INTO `bmsauth` VALUES ('7', 'GROUP_DELETE', '分组删除', '', '', '2014-08-27 15:42:18', 'admin', '2014-08-27 15:42:18');
INSERT INTO `bmsauth` VALUES ('8', 'GROUP_UPDATE', '分组更新', '', '', '2014-08-27 15:42:18', 'admin', '2014-08-27 15:42:18');
INSERT INTO `bmsauth` VALUES ('9', 'GROUP_MENU', '分组菜单管理', '', '', '2014-08-27 15:42:18', 'admin', '2014-08-27 15:42:18');
INSERT INTO `bmsauth` VALUES ('10', 'USER_GROUP_VIEW', '分组用户查看', '', '', '2014-08-27 15:42:18', 'admin', '2014-08-27 15:42:18');
INSERT INTO `bmsauth` VALUES ('11', 'USER_GROUP_ADD', '分组用户增加', '', '', '2014-08-27 15:42:18', 'admin', '2014-08-27 15:42:18');
INSERT INTO `bmsauth` VALUES ('12', 'USER_GROUP_DELETE', '分组用户删除', '', '', '2014-08-27 15:42:19', 'admin', '2014-08-27 15:42:19');
INSERT INTO `bmsauth` VALUES ('13', 'USER_GROUP_GROUPS', '用户分组查看', '', '', '2014-08-27 15:42:19', 'admin', '2014-09-09 14:48:58');
INSERT INTO `bmsauth` VALUES ('14', 'USER_GROUP_BIND', '用户分组更新', '', '', '2014-08-27 15:42:19', 'admin', '2014-08-27 15:42:19');

-- ----------------------------
-- Records of bmsauthmapping
-- ----------------------------
INSERT INTO `bmsauthmapping` VALUES ('1', '4', '1', '/mgr/user/view', '', '2014-08-27 15:42:19', 'admin', '2014-08-27 15:42:19');
INSERT INTO `bmsauthmapping` VALUES ('2', '4', '2', '/mgr/user/add', '', '2014-08-27 15:42:19', 'admin', '2014-08-27 15:42:19');
INSERT INTO `bmsauthmapping` VALUES ('3', '4', '3', '/mgr/user/update', '', '2014-08-27 15:42:19', 'admin', '2014-08-27 15:42:19');
INSERT INTO `bmsauthmapping` VALUES ('4', '4', '4', '/mgr/user/delete', '', '2014-08-27 15:42:19', 'admin', '2014-08-27 15:42:19');
INSERT INTO `bmsauthmapping` VALUES ('5', '5', '5', '/mgr/group/view', '', '2014-08-27 15:42:19', 'admin', '2014-08-27 15:42:19');
INSERT INTO `bmsauthmapping` VALUES ('6', '5', '6', '/mgr/group/add', '', '2014-08-27 15:42:20', 'admin', '2014-08-27 15:42:20');
INSERT INTO `bmsauthmapping` VALUES ('7', '5', '7', '/mgr/group/update', '', '2014-08-27 15:42:20', 'admin', '2014-08-27 15:42:20');
INSERT INTO `bmsauthmapping` VALUES ('8', '5', '8', '/mgr/group/delete', '', '2014-08-27 15:42:20', 'admin', '2014-08-27 15:42:20');
INSERT INTO `bmsauthmapping` VALUES ('9', '5', '9', '/mgr/groupmenu', '', '2014-08-27 15:42:20', 'admin', '2014-08-27 15:42:20');
INSERT INTO `bmsauthmapping` VALUES ('10', '5', '10', '/mgr/usergroup/view', '', '2014-08-27 15:42:20', 'admin', '2014-08-27 15:42:20');
INSERT INTO `bmsauthmapping` VALUES ('11', '5', '11', '/mgr/usergroup/add', '', '2014-08-27 15:42:20', 'admin', '2014-08-27 15:42:20');
INSERT INTO `bmsauthmapping` VALUES ('12', '5', '12', '/mgr/usergroup/delete', '', '2014-08-27 15:42:20', 'admin', '2014-08-27 15:42:20');
INSERT INTO `bmsauthmapping` VALUES ('13', '5', '13', '/mgr/usergroup/groups/view', '', '2014-08-27 15:42:21', 'admin', '2014-08-27 15:42:21');
INSERT INTO `bmsauthmapping` VALUES ('14', '5', '14', '/mgr/usergroup/groups/bind', '', '2014-08-27 15:42:21', 'admin', '2014-08-27 15:42:21');


-- ----------------------------
-- Records of bmsgroupmenu
-- ----------------------------
INSERT INTO `bmsgroupmenu` VALUES ('1', '1', '1', '2014-09-10 15:20:04', 'admin', '2014-09-10 15:20:04');
INSERT INTO `bmsgroupmenu` VALUES ('2', '1', '2', '2014-09-10 15:20:04', 'admin', '2014-09-10 15:20:04');
INSERT INTO `bmsgroupmenu` VALUES ('3', '1', '3', '2014-09-10 15:20:04', 'admin', '2014-09-10 15:20:04');
INSERT INTO `bmsgroupmenu` VALUES ('4', '1', '4', '2014-09-10 15:20:04', 'admin', '2014-09-10 15:20:04');
INSERT INTO `bmsgroupmenu` VALUES ('5', '1', '5', '2014-09-10 15:20:04', 'admin', '2014-09-10 15:20:04');