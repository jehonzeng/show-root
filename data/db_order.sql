/*
 Navicat Premium Data Transfer

 Source Server         : 测试（120.77.102.6）
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 120.77.102.6:3307
 Source Schema         : db_order

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/10/2021 09:54:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_back_history
-- ----------------------------
DROP TABLE IF EXISTS `t_back_history`;
CREATE TABLE `t_back_history` (
  `mark_id` varchar(36) NOT NULL,
  `order_no` varchar(50) NOT NULL,
  `add_time` datetime NOT NULL COMMENT '回调时间',
  `pay_type` int(1) NOT NULL DEFAULT '1' COMMENT '1 公众号支付 2 微信网页支付 3 支付宝支付',
  `cid` varchar(36) DEFAULT NULL,
  `order_type` int(1) NOT NULL DEFAULT '1' COMMENT '订单类型  1：普通订单 2：团购订单 3：秒杀订单',
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `unique` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付回调历史信息';

-- ----------------------------
-- Table structure for t_holiday_info
-- ----------------------------
DROP TABLE IF EXISTS `t_holiday_info`;
CREATE TABLE `t_holiday_info` (
  `holiday` date NOT NULL COMMENT '节假日',
  PRIMARY KEY (`holiday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='节假日信息表';

-- ----------------------------
-- Table structure for t_order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `t_order_delivery`;
CREATE TABLE `t_order_delivery` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `order_id` varchar(36) NOT NULL COMMENT '关联订单',
  `contact` varchar(20) NOT NULL COMMENT '联系人',
  `delivery_date` date DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `delivery_address` varchar(200) DEFAULT NULL COMMENT '配送地址',
  `delivery_area` varchar(100) DEFAULT NULL COMMENT '配送地区',
  `province` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `delivery_type` varchar(30) DEFAULT NULL COMMENT '配送方式',
  `order_type` varchar(1) DEFAULT NULL COMMENT '订单类型(1:普通订单, 2: 团购  3：秒杀）',
  `track_no` varchar(36) DEFAULT NULL COMMENT '物流运单编号',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`mark_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='订单配送列表';

-- ----------------------------
-- Table structure for t_order_error
-- ----------------------------
DROP TABLE IF EXISTS `t_order_error`;
CREATE TABLE `t_order_error` (
  `mark_id` varchar(36) NOT NULL,
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `error_info` varchar(255) NOT NULL,
  `error_type` int(11) NOT NULL DEFAULT '0' COMMENT '-1 撤回 0 未处理 1 确认无误',
  `add_time` datetime NOT NULL,
  `user_mark` varchar(36) DEFAULT NULL COMMENT '处理人',
  `order_type` int(1) NOT NULL DEFAULT '1' COMMENT '1: 普通订单 2：团购订单  3：秒杀订单',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_info`;
CREATE TABLE `t_order_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` varchar(36) NOT NULL COMMENT '下单用户',
  `order_amount` decimal(6,2) NOT NULL COMMENT '订单总额',
  `delivery_amount` decimal(6,2) NOT NULL COMMENT '配送费',
  `pay_amount` decimal(6,2) NOT NULL COMMENT '支付总金额',
  `order_time` datetime NOT NULL COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `delivery_date` date DEFAULT NULL COMMENT '配送时间（用户下单确认配送日期）',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `arrive_time` datetime DEFAULT NULL COMMENT '到达时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_type` int(1) DEFAULT NULL COMMENT '订单类型(1：普通订单 2：测试订单 3：运营订单)',
  `order_status` varchar(4) NOT NULL COMMENT '订单状态',
  `coupon_id` varchar(36) DEFAULT NULL COMMENT '关联优惠券',
  `order_source` varchar(10) DEFAULT NULL COMMENT '合作商引流代码',
  `manager_id` varchar(36) DEFAULT NULL COMMENT '口令分享对应的内部人员ID',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `mark_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL COMMENT '订单主键',
  `product_id` varchar(36) NOT NULL COMMENT '关联商品',
  `product_type` int(1) DEFAULT NULL COMMENT '普通商品/菜品券(0：单品， 1：菜品券,2套餐，3附属品)',
  `increase_id` varchar(36) DEFAULT NULL COMMENT '当该商品是加价购的商品时，存储该值',
  `product_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '商品规格集',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `base_price` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '成本价',
  `sale_price` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '当前售价（用户购买时的价格）',
  `pay_amount` decimal(6,2) DEFAULT NULL COMMENT '支付金额（sale_price*quantity）',
  `voucher_ids` varchar(200) DEFAULT NULL COMMENT '关联使用的菜品券id集',
  `storehouse_id` varchar(36) DEFAULT NULL COMMENT '冗余仓库主键（利于支付后减库存）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详细信息表';

-- ----------------------------
-- Table structure for t_order_record
-- ----------------------------
DROP TABLE IF EXISTS `t_order_record`;
CREATE TABLE `t_order_record` (
  `mark_id` varchar(36) DEFAULT NULL,
  `order_no` varchar(50) DEFAULT NULL,
  `reason` text COMMENT '退款原因'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_push_info
-- ----------------------------
DROP TABLE IF EXISTS `t_push_info`;
CREATE TABLE `t_push_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `template_id` varchar(36) DEFAULT NULL COMMENT '模板id',
  `version` int(5) DEFAULT NULL COMMENT '版本',
  `push_info` text COMMENT '内容',
  `status` int(1) DEFAULT NULL COMMENT '状态（0:未使用 1：使用中）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `type_id` varchar(36) DEFAULT NULL COMMENT '类型id(t_push_type表的主键)',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_push_template
-- ----------------------------
DROP TABLE IF EXISTS `t_push_template`;
CREATE TABLE `t_push_template` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `modal_id` varchar(50) DEFAULT NULL COMMENT '推送模板id',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `industry` varchar(50) DEFAULT NULL COMMENT '行业',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_push_type
-- ----------------------------
DROP TABLE IF EXISTS `t_push_type`;
CREATE TABLE `t_push_type` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '类型名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_refund_back
-- ----------------------------
DROP TABLE IF EXISTS `t_refund_back`;
CREATE TABLE `t_refund_back` (
  `mark_id` varchar(36) NOT NULL,
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `refund_no` varchar(50) NOT NULL COMMENT '退款号',
  `refund_status` int(1) NOT NULL COMMENT '0：退款失败 1：退款成功',
  `total_fee` decimal(6,2) NOT NULL COMMENT '退款金额',
  `add_time` datetime NOT NULL,
  `order_type` int(1) DEFAULT '1' COMMENT '订单类型 1：普通订单 2：团购订单 3：秒杀订单',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `theme` varchar(50) DEFAULT NULL COMMENT '活动主题',
  `goods_id` varchar(36) NOT NULL COMMENT '关联商品',
  `seckill_id` varchar(36) NOT NULL COMMENT '关联秒杀',
  `user_id` varchar(36) NOT NULL COMMENT '下单用户',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '商品规格集',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `order_amount` decimal(6,2) NOT NULL COMMENT '订单总额',
  `delivery_amount` decimal(6,2) DEFAULT NULL COMMENT '配送费',
  `pay_amount` decimal(6,2) NOT NULL COMMENT '支付金额',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `delivery_date` date DEFAULT NULL COMMENT '配送时间（用户下单确认配送日期）',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `arrive_time` datetime DEFAULT NULL COMMENT '到达时间',
  `order_source` varchar(4) DEFAULT NULL COMMENT '合作商引流代码',
  `order_status` varchar(4) NOT NULL COMMENT '状态',
  `storehouse_id` varchar(36) DEFAULT NULL COMMENT '冗余仓库主键（利于支付后减库存）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀订单表';

-- ----------------------------
-- Table structure for t_teambuy_group
-- ----------------------------
DROP TABLE IF EXISTS `t_teambuy_group`;
CREATE TABLE `t_teambuy_group` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `group_no` varchar(50) NOT NULL COMMENT '团单号',
  `type` int(1) NOT NULL COMMENT '团购类型(1 老带新  2 全民)',
  `theme` varchar(100) DEFAULT NULL COMMENT '活动主题',
  `goods_id` varchar(36) DEFAULT NULL COMMENT '关联商品或菜品券',
  `super_no` varchar(50) DEFAULT NULL COMMENT '合团目标团单组mark',
  `teambuy_id` varchar(36) NOT NULL COMMENT '关联团购活动信息',
  `creator` varchar(36) DEFAULT NULL COMMENT '发起拼团时间（第一个人开团支付时间）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `req_count` int(11) DEFAULT NULL,
  `current_count` int(7) NOT NULL DEFAULT '1' COMMENT '参团人数（默认人数1）',
  `vaild_time` decimal(6,1) NOT NULL DEFAULT '0.0' COMMENT '成团有效时间',
  `group_status` varchar(4) NOT NULL DEFAULT '1' COMMENT '团单状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团购单主表';

-- ----------------------------
-- Table structure for t_teambuy_order
-- ----------------------------
DROP TABLE IF EXISTS `t_teambuy_order`;
CREATE TABLE `t_teambuy_order` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `goods_id` varchar(36) NOT NULL COMMENT '关联商品',
  `group_id` varchar(36) NOT NULL COMMENT '关联团购团单组',
  `user_id` varchar(36) NOT NULL COMMENT '下单用户',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '商品规格集',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `order_amount` decimal(6,2) NOT NULL COMMENT '订单总额',
  `delivery_amount` decimal(6,2) DEFAULT NULL COMMENT '配送费',
  `pay_amount` decimal(6,2) NOT NULL COMMENT '支付金额',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `delivery_date` date DEFAULT NULL COMMENT '配送时间（用户下单确认配送日期）',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `arrive_time` datetime DEFAULT NULL COMMENT '到达时间',
  `order_source` varchar(4) DEFAULT NULL COMMENT '合作商引流代码',
  `order_status` varchar(4) NOT NULL COMMENT '状态',
  `storehouse_id` varchar(36) DEFAULT NULL COMMENT '冗余仓库主键（利于支付后减库存）',
  `sort` int(11) DEFAULT '99' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团单子订单表';

-- ----------------------------
-- Table structure for t_track_info
-- ----------------------------
DROP TABLE IF EXISTS `t_track_info`;
CREATE TABLE `t_track_info` (
  `mark_id` varchar(36) NOT NULL,
  `com` varchar(10) NOT NULL COMMENT '快递公司编码（对应快递100的快递公司编码）',
  `track_no` varchar(36) NOT NULL,
  `state` int(1) NOT NULL COMMENT '快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转投 等7个状态',
  `info` text NOT NULL COMMENT '请求快递100返回的结果',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_user_address
-- ----------------------------
DROP TABLE IF EXISTS `t_user_address`;
CREATE TABLE `t_user_address` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `user_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话',
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `province` varchar(20) DEFAULT NULL COMMENT '省',
  `user_address` varchar(200) DEFAULT NULL COMMENT '地址',
  `address_type` int(11) DEFAULT NULL COMMENT '地址类型',
  `user_id` varchar(36) DEFAULT NULL COMMENT '关联用户',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `default_or` bit(1) DEFAULT NULL COMMENT '默认地址否',
  `server_status` bit(1) DEFAULT NULL COMMENT '状态',
  `send_today` bit(1) DEFAULT NULL COMMENT '极客当天是否可送达',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户地址表';

-- ----------------------------
-- Table structure for t_user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `t_user_coupon`;
CREATE TABLE `t_user_coupon` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '关联用户',
  `template_id` varchar(36) DEFAULT NULL COMMENT '券所关联的全模板',
  `server_status` int(1) DEFAULT NULL COMMENT '使用状态：-1已过期、0未使用、1已使用	',
  `start_time` datetime DEFAULT NULL COMMENT '有效开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '有效结束时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `limit_price` decimal(6,2) DEFAULT NULL COMMENT '满减额度',
  `coupon_price` decimal(6,2) DEFAULT NULL COMMENT '券金额',
  `coupon_discount` decimal(2,1) DEFAULT NULL COMMENT '券折扣（0.1-0.9）	',
  `coupon_name` varchar(50) DEFAULT NULL COMMENT '券名称',
  `link_id` varchar(36) DEFAULT NULL COMMENT '关联商品或者分类',
  `link_name` varchar(50) DEFAULT NULL COMMENT '商品名称或者分类名',
  `coupon_type` int(1) DEFAULT NULL COMMENT '券类型(0 : 线下代金券 1：线上优惠券)',
  `limit_region` varchar(36) DEFAULT NULL COMMENT '门店限制',
  `limit_time` varchar(255) DEFAULT NULL COMMENT '使用时间（多个日期）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户券表';

-- ----------------------------
-- Table structure for t_user_voucher
-- ----------------------------
DROP TABLE IF EXISTS `t_user_voucher`;
CREATE TABLE `t_user_voucher` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) NOT NULL COMMENT '关联用户',
  `voucher_id` varchar(36) NOT NULL COMMENT '对应菜品券id',
  `voucher_name` varchar(50) DEFAULT NULL COMMENT '菜品券名称',
  `product_id` varchar(36) NOT NULL COMMENT '关联商品',
  `product_type` int(1) NOT NULL COMMENT '0 普通商品 1套餐商品',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '商品规格集',
  `order_type` int(11) NOT NULL COMMENT '下单类型(-1:非购买 1:普通订单, 2: 团购  3：秒杀)',
  `order_no` varchar(20) DEFAULT NULL COMMENT '下单号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `quantity` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户商品券表';

SET FOREIGN_KEY_CHECKS = 1;
