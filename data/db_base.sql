/*
 Navicat Premium Data Transfer

 Source Server         : 测试（120.77.102.6）
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 120.77.102.6:3307
 Source Schema         : db_base

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/10/2021 09:53:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_action_info
-- ----------------------------
DROP TABLE IF EXISTS `t_action_info`;
CREATE TABLE `t_action_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `action_code` varchar(20) NOT NULL,
  `memo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `unique` (`action_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_action_item
-- ----------------------------
DROP TABLE IF EXISTS `t_action_item`;
CREATE TABLE `t_action_item` (
  `mark_id` varchar(36) NOT NULL,
  `sort` int(2) NOT NULL DEFAULT '99',
  `action_code` varchar(20) NOT NULL,
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '0 文本 1 图片 2 图文 3 模板',
  `content` varchar(250) DEFAULT NULL,
  `template_mark` varchar(36) DEFAULT NULL,
  `image_path` varchar(36) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `image_url` varchar(200) DEFAULT NULL,
  `link_url` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_area_info
-- ----------------------------
DROP TABLE IF EXISTS `t_area_info`;
CREATE TABLE `t_area_info` (
  `num` varchar(6) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `super_id` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='级联省市区表';

-- ----------------------------
-- Table structure for t_attribute_info
-- ----------------------------
DROP TABLE IF EXISTS `t_attribute_info`;
CREATE TABLE `t_attribute_info` (
  `mark_id` bigint(36) NOT NULL,
  `code` varchar(15) NOT NULL COMMENT '属性编码（如：TS01）',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0失效 1有效',
  `type` varchar(10) NOT NULL COMMENT '属性类型（TS：口味,其他自定义）',
  `name` varchar(50) NOT NULL COMMENT '属性名称',
  `father` varchar(36) DEFAULT NULL COMMENT '属性上级',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT '99' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统属性表';

-- ----------------------------
-- Table structure for t_counsel_info
-- ----------------------------
DROP TABLE IF EXISTS `t_counsel_info`;
CREATE TABLE `t_counsel_info` (
  `mark_id` varchar(36) NOT NULL DEFAULT '',
  `nick_name` varchar(20) NOT NULL COMMENT '名称',
  `user_advise` varchar(255) NOT NULL COMMENT '意见反馈',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱地址',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网咨询表';

-- ----------------------------
-- Table structure for t_coupon_template
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon_template`;
CREATE TABLE `t_coupon_template` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `coupon_name` varchar(50) DEFAULT NULL,
  `coupon_total` int(11) DEFAULT NULL COMMENT '发放总量',
  `coupon_type` int(1) NOT NULL DEFAULT '1' COMMENT '券类型：0 : 线下  1：线上',
  `server_type` int(1) NOT NULL COMMENT '优惠形式（0现金，1折扣）',
  `coupon_price` decimal(6,2) DEFAULT NULL COMMENT '券金额',
  `coupon_discount` decimal(3,2) DEFAULT NULL COMMENT '折扣',
  `limit_price` decimal(6,2) DEFAULT '0.00' COMMENT '限制最低价格（门槛）',
  `server_status` bit(1) DEFAULT b'0',
  `limit_count` int(11) NOT NULL DEFAULT '1' COMMENT '每人限领数量',
  `validity_type` int(11) NOT NULL COMMENT '有效期类型（0固定时间 1领到券后N天内有效）',
  `start_time` datetime DEFAULT NULL COMMENT '固定时间：开始时间	',
  `stop_time` datetime DEFAULT NULL COMMENT '固定时间：结束时间	',
  `validity_day` int(11) DEFAULT NULL COMMENT '有效天数N天	',
  `range_type` int(11) NOT NULL COMMENT '可使用商品范围：0全部商品 1指定商品 2指定商品分类',
  `range_id` varchar(36) DEFAULT NULL COMMENT '关联指定商品或商品分类',
  `description` varchar(100) DEFAULT NULL COMMENT '说明',
  `limit_region` varchar(36) DEFAULT NULL COMMENT '门店限制',
  `limit_time` varchar(255) DEFAULT NULL COMMENT '使用时间（多个日期）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='券模板';

-- ----------------------------
-- Table structure for t_feedback_info
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback_info`;
CREATE TABLE `t_feedback_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `type_id` varchar(10) NOT NULL COMMENT '关联类型',
  `content` text COMMENT '内容',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `processor` varchar(50) DEFAULT NULL COMMENT '处理人',
  `description` text COMMENT '处理说明',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `server_status` bit(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题反馈信息表';

-- ----------------------------
-- Table structure for t_image_info
-- ----------------------------
DROP TABLE IF EXISTS `t_image_info`;
CREATE TABLE `t_image_info` (
  `mark_id` varchar(36) NOT NULL,
  `image_path` varchar(250) NOT NULL COMMENT '图片地址（相对于服务器）',
  `file_type` varchar(10) NOT NULL COMMENT '图片格式',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片信息表';

-- ----------------------------
-- Table structure for t_nav_info
-- ----------------------------
DROP TABLE IF EXISTS `t_nav_info`;
CREATE TABLE `t_nav_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '图标识码',
  `nav_code` varchar(20) NOT NULL COMMENT '标识码',
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '导航类型(0 轮播 1 弹窗 2广告 )',
  `add_time` datetime DEFAULT NULL,
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态 0无效 1有效',
  `remark` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='弹窗&&banner&&中间广告信息主表';

-- ----------------------------
-- Table structure for t_nav_item
-- ----------------------------
DROP TABLE IF EXISTS `t_nav_item`;
CREATE TABLE `t_nav_item` (
  `mark_id` varchar(36) NOT NULL,
  `nav_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联基础信息',
  `theme` varchar(100) DEFAULT NULL COMMENT '主题',
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '0：商品ID 1:内部跳转 2：外部链接',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `show_type` int(1) NOT NULL DEFAULT '0' COMMENT '0 全显示 1 工作日显示 2 假日显示',
  `image_path` varchar(36) DEFAULT NULL COMMENT '关联图片',
  `link_url` varchar(150) DEFAULT NULL COMMENT '商品ID或内部路由名称或外部链接地址(活动，券)',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '图片显示',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='弹窗&&banner&&中间广告副表';

-- ----------------------------
-- Table structure for t_packs_info
-- ----------------------------
DROP TABLE IF EXISTS `t_packs_info`;
CREATE TABLE `t_packs_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '活动状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_packs_item
-- ----------------------------
DROP TABLE IF EXISTS `t_packs_item`;
CREATE TABLE `t_packs_item` (
  `mark_id` varchar(36) NOT NULL,
  `packs_id` varchar(36) DEFAULT NULL,
  `template_id` varchar(36) DEFAULT NULL,
  `server_status` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_product_content
-- ----------------------------
DROP TABLE IF EXISTS `t_product_content`;
CREATE TABLE `t_product_content` (
  `mark_id` varchar(36) NOT NULL,
  `product_id` varchar(36) NOT NULL,
  `content` text COMMENT '详细内容',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网产品内容表';

-- ----------------------------
-- Table structure for t_product_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_info`;
CREATE TABLE `t_product_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0不显示  1显示',
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '0 菜品 1新闻',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `material` varchar(150) DEFAULT NULL COMMENT '主要材料',
  `img_path` varchar(150) DEFAULT NULL COMMENT '图片地址',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT '99' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='官网产品表';

-- ----------------------------
-- Table structure for t_reply_info
-- ----------------------------
DROP TABLE IF EXISTS `t_reply_info`;
CREATE TABLE `t_reply_info` (
  `mark_id` varchar(36) NOT NULL,
  `msg_info` varchar(20) NOT NULL,
  `action_code` varchar(20) DEFAULT NULL,
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0 失效 1 有效',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `unique` (`msg_info`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_scan_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_scan_reply`;
CREATE TABLE `t_scan_reply` (
  `mark_id` varchar(36) NOT NULL,
  `scan_code` varchar(20) NOT NULL COMMENT '二维码代码',
  `code_url` varchar(255) DEFAULT NULL COMMENT '二维码图片',
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '0 文本 1 图片 2 图文 3 模板 4抽奖 5小程序卡片推送',
  `content` text,
  `template_mark` varchar(36) DEFAULT NULL,
  `image_path` varchar(36) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `link_url` varchar(200) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '图文描述',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '结束时间',
  `server_status` bit(1) DEFAULT NULL,
  `sort` int(255) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_store_info
-- ----------------------------
DROP TABLE IF EXISTS `t_store_info`;
CREATE TABLE `t_store_info` (
  `mark_id` varchar(36) CHARACTER SET utf8mb4 NOT NULL,
  `store_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '门店名称',
  `address` varchar(150) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '门店地址',
  `scavenger` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '扫码人',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态 0无效 1有效',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门店区域限制表';

-- ----------------------------
-- Table structure for t_store_item
-- ----------------------------
DROP TABLE IF EXISTS `t_store_item`;
CREATE TABLE `t_store_item` (
  `store_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`store_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门店员工表';

-- ----------------------------
-- Table structure for t_sys_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_info`;
CREATE TABLE `t_sys_info` (
  `name` varchar(20) NOT NULL,
  `data_json` text NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信菜单信息表';

SET FOREIGN_KEY_CHECKS = 1;
