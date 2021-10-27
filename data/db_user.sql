/*
 Navicat Premium Data Transfer

 Source Server         : 测试（120.77.102.6）
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 120.77.102.6:3307
 Source Schema         : db_user

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/10/2021 09:54:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_manager_code
-- ----------------------------
DROP TABLE IF EXISTS `t_manager_code`;
CREATE TABLE `t_manager_code` (
  `mark_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL COMMENT '内部人员ID',
  `code` varchar(255) NOT NULL COMMENT '内部人员口令',
  `discount` decimal(3,2) NOT NULL COMMENT '优惠折扣(0.10-0.99)',
  `server_status` bit(1) NOT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内部人员口令表';

-- ----------------------------
-- Table structure for t_partner_info
-- ----------------------------
DROP TABLE IF EXISTS `t_partner_info`;
CREATE TABLE `t_partner_info` (
  `mark_id` varchar(36) NOT NULL,
  `identification_code` varchar(10) DEFAULT NULL COMMENT '标识码',
  `name` varchar(20) DEFAULT NULL COMMENT '合作商名称',
  `add_time` datetime DEFAULT NULL COMMENT '加盟时间',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
  `address` varchar(150) DEFAULT NULL COMMENT '联系地址',
  `server_status` bit(1) DEFAULT NULL COMMENT '有效否',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_role_info
-- ----------------------------
DROP TABLE IF EXISTS `t_role_info`;
CREATE TABLE `t_role_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `server_status` bit(1) DEFAULT b'0' COMMENT '状态 有效否',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `mark_id` varchar(36) NOT NULL DEFAULT '' COMMENT '主键',
  `nick_name` varchar(50) NOT NULL COMMENT '昵称',
  `real_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `header_img` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `gender` int(1) DEFAULT NULL COMMENT '性别',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `country` varchar(20) DEFAULT NULL COMMENT '国家',
  `language` varchar(10) DEFAULT NULL COMMENT '语言',
  `user_level` varchar(4) DEFAULT NULL COMMENT '用户等级',
  `wopen_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公众号唯一openid',
  `xopen_id` varchar(50) DEFAULT NULL COMMENT '小程序唯一openid',
  `union_id` varchar(50) DEFAULT NULL COMMENT '微信unionID，关联微信所有平台',
  `create_time` datetime NOT NULL,
  `wechat_status` int(1) NOT NULL DEFAULT '1' COMMENT '微信关注状态  0 未关注  1已关注  2已取关',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for t_user_integral
-- ----------------------------
DROP TABLE IF EXISTS `t_user_integral`;
CREATE TABLE `t_user_integral` (
  `mark_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `integral_limit` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分记录表';

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `role_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for t_user_token
-- ----------------------------
DROP TABLE IF EXISTS `t_user_token`;
CREATE TABLE `t_user_token` (
  `mark_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `refresh_time` datetime DEFAULT NULL,
  `token` varchar(36) NOT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='token表';

-- ----------------------------
-- Table structure for t_wechat_info
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat_info`;
CREATE TABLE `t_wechat_info` (
  `open_id` varchar(50) NOT NULL COMMENT '微信公众号唯一',
  `nick_name` varchar(30) NOT NULL COMMENT '昵称',
  `header_img` varchar(200) DEFAULT NULL COMMENT '头像',
  `source` varchar(50) DEFAULT NULL COMMENT '微信关注来源',
  `wechat_status` int(1) NOT NULL COMMENT '微信状态（ -1 未绑定 0 取关 1 关注 ）',
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信表';

-- ----------------------------
-- Table structure for t_xwechat_info
-- ----------------------------
DROP TABLE IF EXISTS `t_xwechat_info`;
CREATE TABLE `t_xwechat_info` (
  `open_id` varchar(50) NOT NULL,
  `nick_name` varchar(20) DEFAULT NULL,
  `header_img` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序表';

SET FOREIGN_KEY_CHECKS = 1;
