/*
 Navicat Premium Data Transfer

 Source Server         : mysql-local.home
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : local.home:3306
 Source Schema         : carp_ddd

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 17/02/2020 15:34:22
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='DB WorkerID Assigner for UID Generator';


-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(32) NOT NULL COMMENT '客户端ID(Http Basic 用户名)',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端密码(Http Basic 密码)',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '(Http Basic)认证方式(可多选),[password:密码,authorization_code:授权码,refresh_token:刷新令牌,implicit:隐式,client_credentials:客户端证书\n]',
  `scope` varchar(256) DEFAULT NULL COMMENT '可访问范围',
  `authorities` varchar(512) DEFAULT NULL COMMENT '权限(client_credentials授权方式不会读取用户权限，会该权限）',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源ID',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '令牌时效(秒)',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '刷新令牌时效(秒)',
  `autoapprove` varchar(32) DEFAULT NULL COMMENT '授权码认证方式(单选)[true,false,read,write]',
  `web_server_redirect_uri` varchar(512) DEFAULT NULL COMMENT '授权码模式跳转URL',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '附件信息',
  PRIMARY KEY (`client_id`),
  KEY `idx_grant_type` (`authorized_grant_types`) USING BTREE,
  KEY `idx_authorities` (`authorities`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户端信息表';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('carp', '{noop}carp', 'password,refresh_token,authorization_code,client_credentials', 'server', 'ROLE_ADMIN', 'carp-ums', 86400, 2592000, 'true', 'http://baidu.com', NULL);
COMMIT;

-- ----------------------------
-- Table structure for ums_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_permission`;
CREATE TABLE `ums_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_code` varchar(50) NOT NULL DEFAULT '' COMMENT '权限编码',
  `permission_name` varchar(50) NOT NULL DEFAULT '' COMMENT '权限名称',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '权限描述',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用标识, 0:启用, 1:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of ums_permission
-- ----------------------------
BEGIN;
INSERT INTO `ums_permission` VALUES (1, 'SUPER', '超级用户', '超级用户', 0, '2019-12-28 20:14:07', NULL);
INSERT INTO `ums_permission` VALUES (2, 'dashboard', '控制面板', '控制面板', 0, '2020-02-16 13:44:50', NULL);
COMMIT;

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(50) NOT NULL DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '角色描述',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁用标识, 0:启用, 1:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
BEGIN;
INSERT INTO `ums_role` VALUES (1, 'developer', '开发人员', '开发', 0, '2019-12-27 17:15:45', NULL);
INSERT INTO `ums_role` VALUES (2, 'admin', '管理员', '管理员', 0, '2019-12-27 17:16:07', NULL);
INSERT INTO `ums_role` VALUES (3, 'tester', '测试人员', '测试', 0, '2019-12-28 20:12:18', '2019-12-28 20:12:50');
COMMIT;

-- ----------------------------
-- Table structure for ums_role_permission_link
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_permission_link`;
CREATE TABLE `ums_role_permission_link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_role_premission` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `email` varchar(63) NOT NULL COMMENT '邮箱',
  `nickname` varchar(40) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `salt` varchar(255) NOT NULL DEFAULT '' COMMENT '随机盐',
  `disable` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '禁用标识[0:启用, 1:禁用]',
  `locked` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '锁定标识[0:未锁定, 1:锁定]',
  `remark` varchar(63) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`username`) COMMENT '用户名唯一约束',
  UNIQUE KEY `uniq_phone` (`phone`) COMMENT '手机号唯一约束'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of ums_user
-- ----------------------------
BEGIN;
INSERT INTO `ums_user` VALUES (1, 'admin', '12345678', '15988134706', 'rxyor@outlook.com', '刘阳', 'https://portrait.gitee.com/uploads/avatars/user/555/1667394_liuyangrxy_1578957274.png', '', 0, 0, 'IU', '2019-12-28 21:24:25', '2020-02-17 07:18:23');
INSERT INTO `ums_user` VALUES (2, 'liuyang', '12345678', '15988126666', 'liuyang@outlook.com', '小菜菜', 'https://portrait.gitee.com/uploads/avatars/user/555/1667394_liuyangrxy_1578957274.png', '', 0, 0, NULL, '2020-02-17 05:19:12', '2020-02-17 05:19:18');
COMMIT;

-- ----------------------------
-- Table structure for ums_user_role_link
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role_link`;
CREATE TABLE `ums_user_role_link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of ums_user_role_link
-- ----------------------------
BEGIN;
INSERT INTO `ums_user_role_link` VALUES (1, 1, 1, '2020-02-17 04:35:13');
INSERT INTO `ums_user_role_link` VALUES (2, 1, 2, '2020-02-17 04:35:13');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
