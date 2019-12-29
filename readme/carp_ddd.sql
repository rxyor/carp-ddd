/*
 Navicat Premium Data Transfer

 Source Server         : mysql-127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 127.0.0.1:3306
 Source Schema         : carp_ddd

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 29/12/2019 20:26:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for WORKER_NODE
-- ----------------------------
DROP TABLE IF EXISTS `WORKER_NODE`;
CREATE TABLE `WORKER_NODE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
  `HOST_NAME` varchar(64) NOT NULL COMMENT 'host name',
  `PORT` varchar(64) NOT NULL COMMENT 'port',
  `TYPE` int(11) NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
  `LAUNCH_DATE` date NOT NULL COMMENT 'launch date',
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'created time',
  `MODIFIED` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'modified time',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COMMENT='DB WorkerID Assigner for UID Generator';

-- ----------------------------
-- Records of WORKER_NODE
-- ----------------------------
BEGIN;
INSERT INTO `WORKER_NODE` VALUES (49, '192.168.0.102', '1577539183975-83964', 2, '2019-12-28', '2019-12-28 21:19:44', '2019-12-28 21:19:44');
INSERT INTO `WORKER_NODE` VALUES (50, '192.168.0.102', '1577539210443-58184', 2, '2019-12-28', '2019-12-28 21:20:10', '2019-12-28 21:20:10');
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_code` varchar(50) NOT NULL DEFAULT '' COMMENT '权限编码',
  `permission_name` varchar(50) NOT NULL DEFAULT '' COMMENT '权限名称',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '权限描述',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用标识, 0:启用, 1:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_permission_code` (`permission_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (3, 'SUPER', '超级用户', '超级用户', 0, '2019-12-28 20:14:07', NULL);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(50) NOT NULL DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '角色描述',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用标识, 0:启用, 1:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'developer', '开发人员', '开发', 0, '2019-12-27 17:15:45', NULL);
INSERT INTO `role` VALUES (2, 'admin', '管理员', '管理员', 0, '2019-12-27 17:16:07', NULL);
INSERT INTO `role` VALUES (3, 'tester', '测试人员', '测试', 0, '2019-12-28 20:12:18', '2019-12-28 20:12:50');
COMMIT;

-- ----------------------------
-- Table structure for role_permission_link
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_link`;
CREATE TABLE `role_permission_link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_role_premission` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `nickname` varchar(40) NOT NULL COMMENT '昵称',
  `salt` varchar(255) NOT NULL DEFAULT '' COMMENT '随机盐',
  `disable` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '禁用标识[0:启用, 1:禁用]',
  `locked` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '锁定标识[0:未锁定, 1:锁定]',
  `remark` varchar(63) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`username`) COMMENT '用户名唯一约束',
  UNIQUE KEY `uniq_phone` (`phone`) COMMENT '手机号唯一约束'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '1VWFNJHDUCZCWCY8', '12345678', '15988134706', '刘阳', '', 0, 0, 'IU', '2019-12-28 21:24:25', NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_role_link
-- ----------------------------
DROP TABLE IF EXISTS `user_role_link`;
CREATE TABLE `user_role_link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of user_role_link
-- ----------------------------
BEGIN;
INSERT INTO `user_role_link` VALUES (1, 1, 1, '2019-12-27 17:16:30');
INSERT INTO `user_role_link` VALUES (2, 1, 2, '2019-12-27 17:16:36');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
