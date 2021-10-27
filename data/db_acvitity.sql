/*
 Navicat Premium Data Transfer

 Source Server         : 测试（120.77.102.6）
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 120.77.102.6:3307
 Source Schema         : db_acvitity

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/10/2021 09:53:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity_gift
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_gift`;
CREATE TABLE `t_activity_gift` (
  `mark_id` varchar(36) NOT NULL,
  `gift_id` varchar(36) NOT NULL COMMENT '关联奖品',
  `gift_name` varchar(50) DEFAULT NULL,
  `activity_id` varchar(36) NOT NULL COMMENT '关联活动',
  `part_type` int(1) NOT NULL DEFAULT '0' COMMENT '参与者类型：（0 发起者(参与者) 1助力者）',
  `point` int(2) NOT NULL DEFAULT '1' COMMENT '兑换分数',
  `pay_price` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '礼物价格',
  `gift_total` int(7) NOT NULL DEFAULT '1' COMMENT '礼物总数量',
  `exchange_total` int(7) NOT NULL DEFAULT '0' COMMENT '已兑换数量',
  `grant_type` int(2) NOT NULL DEFAULT '0' COMMENT '礼物发放方式：0:手动发放;1:自动发放;',
  `limit_type` varchar(4) DEFAULT NULL COMMENT '限制方式:0:总数限量;1:每天限量;2:每周限量;3:每月限量',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态（0 失效 1有效）',
  `sort` int(11) NOT NULL DEFAULT '20',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参与活动礼物表';

-- ----------------------------
-- Table structure for t_activity_history
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_history`;
CREATE TABLE `t_activity_history` (
  `mark_id` varchar(36) NOT NULL,
  `activity_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL COMMENT '兑奖时间',
  `server_status` int(1) NOT NULL DEFAULT '0' COMMENT '0:待审核;1:审核通过;2:兑奖',
  `act_gift_id` varchar(36) DEFAULT NULL COMMENT '关联活动奖品id',
  `delivery_time` date DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '活动角色类型（0发起者 1参与者）',
  `activity_name` varchar(50) DEFAULT NULL COMMENT '活动名称',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='兑换历史记录表';

-- ----------------------------
-- Table structure for t_activity_info
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_info`;
CREATE TABLE `t_activity_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) NOT NULL COMMENT '活动主题',
  `start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '活动截止时间',
  `check_time` datetime DEFAULT NULL COMMENT '兑奖开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '兑奖结束时间',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '活动状态0 失效 1有效',
  `award_url` varchar(150) DEFAULT NULL COMMENT '兑奖地址链接（预留）',
  `image_path` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `unique` (`theme`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商城活动信息表';

-- ----------------------------
-- Table structure for t_activity_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_rule`;
CREATE TABLE `t_activity_rule` (
  `mark_id` varchar(36) NOT NULL COMMENT '活动id',
  `follow` int(1) DEFAULT NULL COMMENT '计分方式(0无限制;1:需关注) 暂时没有上',
  `initiator_limit` varchar(4) DEFAULT NULL COMMENT '领奖限制（发起者）(每人一次，每天一次，无限制)',
  `limited` varchar(4) DEFAULT NULL COMMENT '活动限制:0:仅限一次;1:每天一次;',
  `helper_limit` varchar(4) DEFAULT NULL COMMENT '领奖限制（参与者）(每人一次，每天一次，无限制)',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动规则表';

-- ----------------------------
-- Table structure for t_gift_info
-- ----------------------------
DROP TABLE IF EXISTS `t_gift_info`;
CREATE TABLE `t_gift_info` (
  `mark_id` varchar(36) NOT NULL,
  `gift_name` varchar(50) NOT NULL COMMENT '礼物名称(冗余)',
  `product_id` varchar(36) NOT NULL COMMENT '关联商品id',
  `gift_type` int(1) NOT NULL DEFAULT '0' COMMENT '礼物类别：0:商品，1:优惠券，2:代金券，3:商品券;',
  `gift_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态： 无效；有效',
  `price` decimal(6,2) DEFAULT NULL COMMENT '礼物价格',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '规格集',
  `image_path` varchar(36) DEFAULT NULL COMMENT '礼物图片',
  `description` varchar(255) DEFAULT NULL COMMENT '礼物说明',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='礼物基本信息表';

-- ----------------------------
-- Table structure for t_help_limit
-- ----------------------------
DROP TABLE IF EXISTS `t_help_limit`;
CREATE TABLE `t_help_limit` (
  `mark_id` varchar(36) NOT NULL,
  `activity_id` varchar(36) NOT NULL,
  `max_point` int(7) NOT NULL DEFAULT '1' COMMENT '最小分',
  `min_point` int(7) NOT NULL DEFAULT '1' COMMENT '最大分',
  `limit_point` int(7) unsigned zerofill NOT NULL DEFAULT '0000000' COMMENT '限制分',
  `priority` int(2) NOT NULL DEFAULT '20' COMMENT '优先级',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='助力计分规则限制表';

-- ----------------------------
-- Table structure for t_participant_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_participant_relation`;
CREATE TABLE `t_participant_relation` (
  `mark_id` varchar(36) NOT NULL,
  `father_id` varchar(50) NOT NULL COMMENT '发起者id',
  `son_id` varchar(50) NOT NULL COMMENT '助力者(被邀请者)id',
  `activity_id` varchar(36) NOT NULL COMMENT '活动id',
  `add_time` datetime DEFAULT NULL COMMENT '助力时间',
  `refresh_time` datetime DEFAULT NULL COMMENT '切换时间',
  `point` int(7) NOT NULL DEFAULT '1' COMMENT '分数',
  `server_status` int(2) NOT NULL COMMENT '状态 0:失效;1:有效;2:已兑奖(失效);3:已兑奖(有效)',
  `server_type` int(11) DEFAULT NULL COMMENT '计分方式：0:无限制;1:必须关注; 暂时没有上',
  `activity_name` varchar(20) NOT NULL COMMENT '活动名称',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参与者关系表';

-- ----------------------------
-- Table structure for t_scan_win
-- ----------------------------
DROP TABLE IF EXISTS `t_scan_win`;
CREATE TABLE `t_scan_win` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(255) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `stop_time` datetime DEFAULT NULL,
  `winner_num` int(11) NOT NULL DEFAULT '0' COMMENT '每个梯度中奖人数',
  `level_num` int(11) NOT NULL DEFAULT '0' COMMENT '每阶梯扫码人数',
  `winner_total` int(11) DEFAULT NULL COMMENT '中奖人数总数',
  `product_type` int(1) NOT NULL COMMENT '参与中奖的商品类型(0：单品， 1：菜品券,2套餐，3附属品)',
  `product_id` varchar(36) NOT NULL COMMENT '商品id',
  `success_msg` varchar(255) NOT NULL COMMENT '中奖提示信息',
  `fail_msg` varchar(255) NOT NULL COMMENT '未中奖提示信息',
  `scan_code` varchar(10) NOT NULL COMMENT '二维码编号',
  `server_status` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_scan_winner
-- ----------------------------
DROP TABLE IF EXISTS `t_scan_winner`;
CREATE TABLE `t_scan_winner` (
  `win_id` varchar(36) NOT NULL,
  `open_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_scene_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_scene_goods`;
CREATE TABLE `t_scene_goods` (
  `mark_id` varchar(36) NOT NULL,
  `scene_id` varchar(36) NOT NULL,
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `base_price` decimal(6,2) DEFAULT NULL COMMENT '原价',
  `sale_price` decimal(6,2) DEFAULT NULL COMMENT '售价',
  `description` varchar(255) DEFAULT NULL COMMENT '商品简述',
  `image_path` varchar(36) DEFAULT NULL COMMENT '显示图片',
  `stock_size` int(11) DEFAULT NULL COMMENT '库存',
  `receive_size` int(11) DEFAULT '0' COMMENT '兑换数量',
  `server_status` bit(1) DEFAULT NULL,
  `sort` int(7) NOT NULL DEFAULT '99' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线下商品信息表';

-- ----------------------------
-- Table structure for t_scene_info
-- ----------------------------
DROP TABLE IF EXISTS `t_scene_info`;
CREATE TABLE `t_scene_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL COMMENT '主题',
  `promotion_type` int(1) NOT NULL DEFAULT '0' COMMENT '促销方式 1折扣 0减价',
  `discount` decimal(6,2) DEFAULT NULL COMMENT '优惠（折扣或者金额）',
  `limit_region` varchar(36) DEFAULT NULL COMMENT '门店限制',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '结束时间',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态：无效 ，有效',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线下售卖促销表';

-- ----------------------------
-- Table structure for t_scene_item
-- ----------------------------
DROP TABLE IF EXISTS `t_scene_item`;
CREATE TABLE `t_scene_item` (
  `mark_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `goods_id` varchar(36) DEFAULT NULL,
  `goods_name` varchar(50) DEFAULT NULL,
  `quantity` int(7) NOT NULL DEFAULT '1' COMMENT '数量',
  `server_status` bit(1) NOT NULL COMMENT '是否领取 1已领取  0未领取',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线下领取记录表';

-- ----------------------------
-- Table structure for t_scene_order
-- ----------------------------
DROP TABLE IF EXISTS `t_scene_order`;
CREATE TABLE `t_scene_order` (
  `mark_id` varchar(36) NOT NULL,
  `order_no` varchar(36) DEFAULT NULL COMMENT '订单编号（支付使用）',
  `user_id` varchar(36) DEFAULT NULL,
  `order_time` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线下下单记录表';

-- ----------------------------
-- Table structure for t_seckill_info
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_info`;
CREATE TABLE `t_seckill_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(20) NOT NULL COMMENT '主题',
  `description` varchar(200) NOT NULL COMMENT '描述',
  `goods_id` varchar(36) DEFAULT NULL,
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称（冗余）',
  `specification_ids` varchar(255) DEFAULT NULL COMMENT '商品规格集',
  `price` decimal(6,2) DEFAULT NULL COMMENT '秒杀价',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `stop_time` datetime NOT NULL COMMENT '结束时间',
  `total_stock` int(11) NOT NULL COMMENT '参与团购的商品数量',
  `limited` int(11) DEFAULT NULL COMMENT '限购次数',
  `free` bit(1) DEFAULT b'0' COMMENT '是否包邮（0:不包邮  1包邮）',
  `server_status` bit(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀表';

-- ----------------------------
-- Table structure for t_teambuy_info
-- ----------------------------
DROP TABLE IF EXISTS `t_teambuy_info`;
CREATE TABLE `t_teambuy_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(100) NOT NULL COMMENT '活动主题',
  `description` varchar(200) NOT NULL COMMENT '描述',
  `goods_id` varchar(36) NOT NULL COMMENT '关联商品',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称（冗余）',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '商品规格集',
  `type` int(1) DEFAULT NULL COMMENT '团购类型(1 老带新  2 全民)',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `stop_time` datetime NOT NULL COMMENT '结束时间',
  `req_count` int(11) NOT NULL DEFAULT '2' COMMENT '成团人数',
  `vaild_time` int(11) NOT NULL DEFAULT '0' COMMENT '成团有效时间',
  `price` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '团购价',
  `total_stock` int(11) unsigned zerofill DEFAULT NULL COMMENT '参与秒杀的商品数量',
  `limited` int(11) DEFAULT NULL COMMENT '用户限购次数',
  `free` bit(1) DEFAULT b'0' COMMENT '是否包邮（0:不包邮  1包邮）',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0 失效 1有效',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团购信息表';

SET FOREIGN_KEY_CHECKS = 1;
