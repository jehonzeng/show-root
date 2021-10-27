/*
 Navicat Premium Data Transfer

 Source Server         : 测试（120.77.102.6）
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 120.77.102.6:3307
 Source Schema         : db_ordering

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/10/2021 09:54:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_cart_info
-- ----------------------------
DROP TABLE IF EXISTS `t_cart_info`;
CREATE TABLE `t_cart_info` (
  `mark_id` varchar(36) NOT NULL,
  `table_id` varchar(36) DEFAULT NULL,
  `store_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户',
  `commodity_type` varchar(36) DEFAULT NULL COMMENT '商品类型',
  `commodity_id` varchar(36) NOT NULL,
  `commodity_name` varchar(50) DEFAULT NULL,
  `price_id` varchar(36) NOT NULL,
  `specs_items` varchar(255) DEFAULT NULL,
  `cost_price` decimal(6,2) NOT NULL,
  `price_type` int(1) NOT NULL DEFAULT '0' COMMENT '0：售价（sale_price）1:积分兑换（integral_price）',
  `sale_price` decimal(6,2) NOT NULL,
  `integral_price` int(10) DEFAULT '0' COMMENT '积分价',
  `quantity` int(11) NOT NULL DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_category_commodity
-- ----------------------------
DROP TABLE IF EXISTS `t_category_commodity`;
CREATE TABLE `t_category_commodity` (
  `category_id` varchar(36) NOT NULL,
  `commodity_id` varchar(36) NOT NULL,
  `sort` int(11) DEFAULT '99',
  PRIMARY KEY (`category_id`,`commodity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_category_info
-- ----------------------------
DROP TABLE IF EXISTS `t_category_info`;
CREATE TABLE `t_category_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  `img_id` varchar(255) DEFAULT NULL,
  `store_id` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_category_specs
-- ----------------------------
DROP TABLE IF EXISTS `t_category_specs`;
CREATE TABLE `t_category_specs` (
  `category_id` varchar(36) NOT NULL,
  `specs_id` varchar(36) NOT NULL,
  `sort` int(11) DEFAULT '99'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_commodity_img
-- ----------------------------
DROP TABLE IF EXISTS `t_commodity_img`;
CREATE TABLE `t_commodity_img` (
  `commodity_id` varchar(36) NOT NULL,
  `img_id` varchar(36) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '冗余字段',
  `sort` int(11) NOT NULL DEFAULT '99',
  PRIMARY KEY (`commodity_id`,`img_id`),
  KEY `img_index` (`commodity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_commodity_info
-- ----------------------------
DROP TABLE IF EXISTS `t_commodity_info`;
CREATE TABLE `t_commodity_info` (
  `mark_id` varchar(36) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `store_id` varchar(36) NOT NULL COMMENT '关联门店',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0 : 商品  1套餐',
  `introduction` varchar(255) DEFAULT NULL COMMENT '商品简介',
  `quantity` int(11) DEFAULT NULL COMMENT '当不为null时，商品进行清算',
  `in_discount` bit(1) DEFAULT b'1' COMMENT '是否参与优惠',
  `creator` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_commodity_item
-- ----------------------------
DROP TABLE IF EXISTS `t_commodity_item`;
CREATE TABLE `t_commodity_item` (
  `commodity_id` varchar(36) NOT NULL,
  `specs_id` varchar(36) NOT NULL COMMENT '对应t_specs_info中的主键',
  `item_id` varchar(36) NOT NULL COMMENT '对应t_specs_item主键',
  `name` varchar(255) DEFAULT NULL COMMENT '冗余字段',
  `markup_price` decimal(6,2) DEFAULT NULL COMMENT '加价',
  `sort` int(11) DEFAULT '99',
  `checked` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`commodity_id`,`specs_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_commodity_price
-- ----------------------------
DROP TABLE IF EXISTS `t_commodity_price`;
CREATE TABLE `t_commodity_price` (
  `mark_id` varchar(36) NOT NULL DEFAULT '',
  `commodity_id` varchar(36) NOT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `min_point` int(11) DEFAULT NULL,
  `base_point` int(11) DEFAULT NULL,
  `max_point` int(11) DEFAULT NULL,
  `base_price` decimal(6,2) NOT NULL COMMENT '成本价',
  `price_type` int(1) NOT NULL DEFAULT '0' COMMENT '0：售价（sale_price）1:积分兑换（integral_price）',
  `sale_price` decimal(6,2) DEFAULT NULL COMMENT '售价',
  `integral_price` int(10) DEFAULT '0' COMMENT '积分价',
  `defaults` bit(1) DEFAULT b'0',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_commodity_specs
-- ----------------------------
DROP TABLE IF EXISTS `t_commodity_specs`;
CREATE TABLE `t_commodity_specs` (
  `commodity_id` varchar(36) NOT NULL,
  `specs_id` varchar(36) NOT NULL,
  `min_value` int(11) DEFAULT NULL,
  `max_value` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`commodity_id`,`specs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_discount_info
-- ----------------------------
DROP TABLE IF EXISTS `t_discount_info`;
CREATE TABLE `t_discount_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(255) DEFAULT NULL COMMENT '名称',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '优惠形式:0减额 1折扣',
  `discount` decimal(7,2) DEFAULT NULL COMMENT '金额或者折扣',
  `start_time` datetime DEFAULT NULL COMMENT '有效时间：开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '有效时间：结束时间',
  `employ_ids` varchar(255) DEFAULT NULL COMMENT '指定店员使用',
  `remark` varchar(255) DEFAULT NULL COMMENT '规则说明',
  `modify_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `creator` varchar(36) DEFAULT NULL,
  `store_id` varchar(36) DEFAULT NULL COMMENT '门店',
  `sort` int(11) NOT NULL DEFAULT '99' COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '有效状态(-1 删除  0 有效  1有效)',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_employee_info
-- ----------------------------
DROP TABLE IF EXISTS `t_employee_info`;
CREATE TABLE `t_employee_info` (
  `mark_id` varchar(36) NOT NULL,
  `code` varchar(50) DEFAULT NULL COMMENT '员工号',
  `name` varchar(50) NOT NULL,
  `gender` int(1) DEFAULT NULL COMMENT '1：男  2：女',
  `phone` varchar(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_identity_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_identity_employee`;
CREATE TABLE `t_identity_employee` (
  `identity_id` varchar(36) NOT NULL,
  `employee_id` varchar(36) NOT NULL,
  PRIMARY KEY (`employee_id`,`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_identity_info
-- ----------------------------
DROP TABLE IF EXISTS `t_identity_info`;
CREATE TABLE `t_identity_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(15) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `authority` varchar(255) DEFAULT NULL COMMENT '路由权限',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建人',
  `store_id` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_indent_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_indent_detail`;
CREATE TABLE `t_indent_detail` (
  `mark_id` varchar(36) NOT NULL,
  `indent_id` varchar(36) NOT NULL,
  `time_code` varchar(36) NOT NULL COMMENT '同一个订单不同时间下菜单，同一时间下的单code一样',
  `user_id` varchar(36) DEFAULT NULL COMMENT '小程序点单人',
  `commodity_type` int(1) DEFAULT NULL,
  `commodity_id` varchar(36) NOT NULL,
  `commodity_name` varchar(50) DEFAULT NULL,
  `price_id` varchar(36) NOT NULL,
  `specs_items` varchar(255) DEFAULT NULL COMMENT '商品规格集',
  `cost_price` decimal(6,2) NOT NULL COMMENT '原价',
  `price_type` int(1) NOT NULL DEFAULT '0' COMMENT '0：售价（sale_price）1:积分兑换（integral_price）',
  `sale_price` decimal(6,2) NOT NULL COMMENT '售价',
  `member_price` decimal(10,2) DEFAULT NULL COMMENT '会员折扣价',
  `integral_price` int(10) DEFAULT '0' COMMENT '积分价',
  `quantity` int(11) DEFAULT NULL,
  `discount_id` varchar(36) DEFAULT NULL COMMENT '当对商品使用优惠时，记录优惠方式',
  `creator` varchar(36) DEFAULT NULL COMMENT '后台点餐人',
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(36) DEFAULT NULL COMMENT '操作人',
  `modify_time` datetime DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '订单明细状态：-1 :退订， 0 ： 待点餐员确认 1：订单商品',
  PRIMARY KEY (`mark_id`),
  KEY `indent_id_index` (`indent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_indent_info
-- ----------------------------
DROP TABLE IF EXISTS `t_indent_info`;
CREATE TABLE `t_indent_info` (
  `mark_id` varchar(36) NOT NULL,
  `indent_no` varchar(20) NOT NULL,
  `store_id` varchar(36) DEFAULT NULL,
  `table_id` varchar(36) NOT NULL,
  `man_num` int(11) DEFAULT NULL,
  `indent_user` varchar(36) DEFAULT NULL COMMENT '下单人员',
  `indent_time` datetime NOT NULL COMMENT '下单时间',
  `member_id` varchar(255) DEFAULT NULL COMMENT '会员主键',
  `base_total` decimal(7,2) DEFAULT NULL COMMENT '订单总成本，用于后期利润计算',
  `indent_total` decimal(7,2) NOT NULL COMMENT '订单总金额',
  `market_discount` decimal(7,2) DEFAULT NULL COMMENT '买减活动优惠的金额',
  `market_ids` varchar(255) DEFAULT NULL COMMENT '买减活动id集合',
  `employee_id` varchar(36) DEFAULT NULL COMMENT '店员开单时记录',
  `temp_num` varchar(255) NOT NULL COMMENT '开桌临时编号',
  `bill_time` datetime DEFAULT NULL COMMENT '结账时间',
  `bill_by` varchar(36) DEFAULT NULL COMMENT '结账人',
  `modifier` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `indent_status` varchar(4) NOT NULL COMMENT 'IS01：已下单，ISO2：确认订单 IS03：预支付 IS04：已结账 IS05：已失效 IS06：已交班 IS07：已评价',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `table_unique` (`table_id`,`temp_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_indent_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_indent_pay`;
CREATE TABLE `t_indent_pay` (
  `mark_id` varchar(36) NOT NULL,
  `indent_id` varchar(36) NOT NULL,
  `pay_id` varchar(36) NOT NULL DEFAULT '',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `pay_name` varchar(50) NOT NULL COMMENT '支付方式名称（例：优惠券抵扣，则为优惠券名称）',
  `discount_id` varchar(36) DEFAULT NULL COMMENT '当使用的是优惠券时的id',
  `amount` decimal(7,2) DEFAULT NULL COMMENT '计算后实收金额',
  `pay_amount` decimal(7,2) NOT NULL COMMENT '结账金额',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户自主支付时记录（小程序）',
  `consumption_id` varchar(36) DEFAULT NULL COMMENT '当会员余额支付时记录消费明细',
  `employee_id` varchar(36) DEFAULT NULL COMMENT '收银员收银时记录',
  `code` varchar(255) DEFAULT NULL COMMENT '用户小程序支付时记录唯一标识码',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1：已撤退 0：未记录到账 1：正常支付  ',
  PRIMARY KEY (`mark_id`),
  KEY `indent_id_index` (`indent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_indent_remark
-- ----------------------------
DROP TABLE IF EXISTS `t_indent_remark`;
CREATE TABLE `t_indent_remark` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键                                                                                                                                                                                         ',
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `indent_id` varchar(36) NOT NULL COMMENT '订单id',
  `store_id` varchar(36) DEFAULT NULL COMMENT '商店id',
  `service_star` int(1) DEFAULT NULL COMMENT '服务满意度（打星）',
  `store_star` int(1) DEFAULT NULL COMMENT '餐店满意度（打星）',
  `dishes_star` int(1) DEFAULT NULL COMMENT '菜品满意度（打星）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `dishes_remark` varchar(255) DEFAULT NULL COMMENT '菜品评论',
  `service_remark` varchar(255) DEFAULT NULL COMMENT '服务评论',
  `other` varchar(255) DEFAULT NULL COMMENT '其他评论',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单评论表';

-- ----------------------------
-- Table structure for t_market_commodity
-- ----------------------------
DROP TABLE IF EXISTS `t_market_commodity`;
CREATE TABLE `t_market_commodity` (
  `mark_id` varchar(36) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '0：购买商品  1：优惠商品',
  `market_id` varchar(36) NOT NULL,
  `commodity_id` varchar(36) NOT NULL,
  `sort` int(11) DEFAULT '99',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_market_info
-- ----------------------------
DROP TABLE IF EXISTS `t_market_info`;
CREATE TABLE `t_market_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `buy_quantity` int(11) NOT NULL COMMENT '购买量',
  `discount_quantity` int(11) NOT NULL COMMENT '优惠量',
  `discount_type` int(11) NOT NULL COMMENT '0：特价  1：折扣',
  `amount` decimal(4,2) NOT NULL,
  `store_id` varchar(36) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `stop_time` datetime DEFAULT NULL,
  `creator` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_pay_back
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_back`;
CREATE TABLE `t_pay_back` (
  `mark_id` varchar(36) NOT NULL,
  `indent_id` varchar(50) NOT NULL COMMENT '订单',
  `back_info` varchar(255) NOT NULL,
  `back_type` int(11) NOT NULL DEFAULT '0' COMMENT '-1 失败 1 成功',
  `add_time` datetime NOT NULL,
  `user_id` varchar(36) DEFAULT NULL COMMENT '支付人',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_info`;
CREATE TABLE `t_pay_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type_id` varchar(36) DEFAULT NULL,
  `discount_type` int(1) DEFAULT NULL COMMENT '优惠方式（0：减价 1：折扣）',
  `receive_amount` decimal(6,2) DEFAULT NULL COMMENT '实收金额',
  `discount_limit` decimal(6,2) DEFAULT NULL COMMENT '优惠门槛',
  `discount_amount` decimal(6,2) DEFAULT NULL COMMENT '优惠金额/折扣（discount_type连用）',
  `store_id` varchar(36) NOT NULL,
  `received` bit(1) DEFAULT b'1' COMMENT '是否实收  1 实收  0 不实收',
  `discount_have` bit(1) DEFAULT NULL COMMENT '是否与会员折扣同享',
  `btn_color` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_pay_refund
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_refund`;
CREATE TABLE `t_pay_refund` (
  `mark_id` varchar(36) NOT NULL,
  `pay_id` varchar(50) NOT NULL COMMENT '订单支付明细',
  `refund_no` varchar(50) NOT NULL COMMENT '退款号',
  `refund_status` int(1) NOT NULL COMMENT '0：申请失败 1：申请成功',
  `back_status` int(1) DEFAULT NULL COMMENT '1：退款成功 2：退款异常  3：退款关闭',
  `total_fee` decimal(6,2) NOT NULL COMMENT '退款金额',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `refund_info` varchar(255) DEFAULT NULL,
  `back_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_pay_type
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_type`;
CREATE TABLE `t_pay_type` (
  `mark_id` varchar(36) NOT NULL,
  `code` varchar(10) NOT NULL COMMENT '支付类型编码',
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `store_id` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT '99',
  `status` int(1) DEFAULT '0' COMMENT '-1  删除  0 不启用  1 启用',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_printer_info
-- ----------------------------
DROP TABLE IF EXISTS `t_printer_info`;
CREATE TABLE `t_printer_info` (
  `mark_id` varchar(36) NOT NULL,
  `printer_code` varchar(50) DEFAULT NULL COMMENT '模板编码(P201,P202,P203,P204....)',
  `remark` varchar(255) DEFAULT NULL COMMENT '打印机名称',
  `port_name` varchar(100) DEFAULT NULL COMMENT '端口名称(串口COM1:9600,N,8,1，并口LPT1，USB：SP-USB001,网口:ip:9100)',
  `port_type` int(10) DEFAULT NULL COMMENT '端口类型(1000串口，1001并口，1002USB,1003网口)',
  `store_id` varchar(36) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `tail` varchar(255) DEFAULT NULL COMMENT '打印单结尾部分',
  `create_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `sort` int(255) NOT NULL DEFAULT '99',
  `server_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0未启用，1启用 ,-1删除）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_printer_item
-- ----------------------------
DROP TABLE IF EXISTS `t_printer_item`;
CREATE TABLE `t_printer_item` (
  `printer_id` varchar(36) NOT NULL,
  `commodity_id` varchar(36) NOT NULL,
  `sort` int(11) DEFAULT '99' COMMENT '排序（商品所在分类排序一致）',
  PRIMARY KEY (`printer_id`,`commodity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_printer_log
-- ----------------------------
DROP TABLE IF EXISTS `t_printer_log`;
CREATE TABLE `t_printer_log` (
  `mark_id` varchar(36) NOT NULL,
  `table_id` varchar(36) DEFAULT NULL,
  `indent_id` varchar(36) DEFAULT NULL,
  `printer_code` varchar(36) NOT NULL COMMENT '关联打印设备',
  `status_code` int(11) DEFAULT NULL COMMENT '打印机返回状态',
  `print_type` int(11) DEFAULT NULL COMMENT '打印类型（1：制作单   2：预览单   3：预结账单  4：结账单）',
  `error_info` text COMMENT '打印返回的状态信息',
  `send_time` datetime DEFAULT NULL COMMENT '发送打印时间',
  `print_data` text NOT NULL COMMENT '点餐平台预览需要的数据',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_specs_info
-- ----------------------------
DROP TABLE IF EXISTS `t_specs_info`;
CREATE TABLE `t_specs_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '规格名',
  `store_id` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_specs_item
-- ----------------------------
DROP TABLE IF EXISTS `t_specs_item`;
CREATE TABLE `t_specs_item` (
  `mark_id` varchar(36) NOT NULL,
  `specs_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '规格值',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_store_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_store_employee`;
CREATE TABLE `t_store_employee` (
  `store_id` varchar(366) NOT NULL,
  `employee_id` varchar(366) NOT NULL,
  PRIMARY KEY (`store_id`,`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_store_info
-- ----------------------------
DROP TABLE IF EXISTS `t_store_info`;
CREATE TABLE `t_store_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `province` varchar(10) NOT NULL COMMENT '省',
  `city` varchar(10) NOT NULL COMMENT '市',
  `area` varchar(10) NOT NULL COMMENT '区',
  `address` varchar(255) NOT NULL,
  `open_business` time NOT NULL,
  `close_business` time NOT NULL,
  `license` varchar(36) DEFAULT NULL COMMENT '营业执照',
  `business_circle` varchar(255) DEFAULT NULL COMMENT '商圈',
  `per_capita` decimal(6,2) DEFAULT NULL COMMENT '人均',
  `per_fee` decimal(6,2) NOT NULL COMMENT '餐位费',
  `phone` varchar(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_table_area
-- ----------------------------
DROP TABLE IF EXISTS `t_table_area`;
CREATE TABLE `t_table_area` (
  `mark_id` varchar(36) NOT NULL,
  `code` varchar(10) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `store_id` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_table_booking
-- ----------------------------
DROP TABLE IF EXISTS `t_table_booking`;
CREATE TABLE `t_table_booking` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键id',
  `booker` varchar(50) NOT NULL COMMENT '预订人（称呼）',
  `phone` varchar(11) NOT NULL COMMENT '电话',
  `store_id` varchar(36) DEFAULT NULL,
  `table_id` varchar(36) DEFAULT NULL COMMENT '桌台id',
  `employee_id` varchar(36) DEFAULT NULL COMMENT '店员id',
  `man_num` int(11) NOT NULL COMMENT '预订人数',
  `booking_time` datetime NOT NULL COMMENT '预订时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(1) DEFAULT NULL COMMENT '预订状态（-1：已取消  0：已失效  1：预订中  2：已使用）',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_table_cls
-- ----------------------------
DROP TABLE IF EXISTS `t_table_cls`;
CREATE TABLE `t_table_cls` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `store_id` varchar(36) NOT NULL,
  `seats` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_table_info
-- ----------------------------
DROP TABLE IF EXISTS `t_table_info`;
CREATE TABLE `t_table_info` (
  `mark_id` varchar(36) NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `seats` int(2) NOT NULL COMMENT '几人桌',
  `area_id` varchar(36) DEFAULT NULL,
  `cls_id` varchar(36) DEFAULT NULL,
  `store_id` varchar(36) NOT NULL,
  `qr_code` varchar(36) DEFAULT NULL COMMENT '餐桌二维码内容',
  `qr_url` varchar(255) DEFAULT NULL COMMENT '餐桌二维码微信端路径',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `man_num` int(11) DEFAULT NULL COMMENT '当前用餐人数',
  `open_time` datetime DEFAULT NULL COMMENT '开桌时间',
  `temp_num` varchar(255) DEFAULT NULL COMMENT '开桌临时编号',
  `table_status` varchar(10) NOT NULL DEFAULT '' COMMENT '餐桌使用状态',
  `status` int(1) DEFAULT '0' COMMENT '该记录状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_table_status
-- ----------------------------
DROP TABLE IF EXISTS `t_table_status`;
CREATE TABLE `t_table_status` (
  `mark_id` varchar(36) NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `store_id` varchar(36) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_tag_commodity
-- ----------------------------
DROP TABLE IF EXISTS `t_tag_commodity`;
CREATE TABLE `t_tag_commodity` (
  `tag_id` varchar(36) NOT NULL,
  `commodity_id` varchar(36) NOT NULL,
  PRIMARY KEY (`commodity_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_tag_info
-- ----------------------------
DROP TABLE IF EXISTS `t_tag_info`;
CREATE TABLE `t_tag_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `img_id` varchar(36) DEFAULT NULL,
  `sort` int(11) DEFAULT '99',
  `store_id` varchar(36) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0 : 无效 1：有效 -1：已删除',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_template_commodity
-- ----------------------------
DROP TABLE IF EXISTS `t_template_commodity`;
CREATE TABLE `t_template_commodity` (
  `template_id` varchar(36) NOT NULL,
  `commodity_id` varchar(36) NOT NULL,
  PRIMARY KEY (`template_id`,`commodity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_ticket_template
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket_template`;
CREATE TABLE `t_ticket_template` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '券名称',
  `type` int(1) DEFAULT NULL COMMENT '券优惠形式：0减价 1折扣  ',
  `description` varchar(255) DEFAULT NULL,
  `limit_price` decimal(7,2) DEFAULT NULL COMMENT '满减门槛 0无门槛',
  `received_count` int(11) NOT NULL DEFAULT '1' COMMENT '领取数量限制',
  `discount` decimal(7,2) DEFAULT NULL COMMENT '金额或者折扣',
  `mode` int(1) NOT NULL DEFAULT '0' COMMENT '有效期模式0 指定日期范围 1 有效天数',
  `start_time` datetime DEFAULT NULL COMMENT '有效时间：开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '有效时间：结束时间',
  `effective_days` int(11) DEFAULT NULL COMMENT '有效天数',
  `overlay_use` int(7) NOT NULL DEFAULT '0' COMMENT '叠加使用限制',
  `special_date` varchar(255) DEFAULT NULL COMMENT '特殊日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '规则说明',
  `rank_ids` varchar(255) DEFAULT NULL COMMENT '会员等级ids集',
  `creator` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `send_count` int(1) NOT NULL DEFAULT '1' COMMENT '发放数量',
  `limit_store` varchar(255) DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 -1 删除 0 无效 1有效',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_user_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_user_ticket`;
CREATE TABLE `t_user_ticket` (
  `mark_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `template_id` varchar(36) NOT NULL COMMENT '券模板指定商品',
  `name` varchar(50) DEFAULT NULL COMMENT '券名称冗余',
  `type` int(1) DEFAULT NULL COMMENT '券优惠形式 0减价 1折扣 ',
  `description` varchar(255) DEFAULT NULL,
  `limit_price` decimal(7,2) DEFAULT NULL COMMENT '满减门槛 （0无门槛）',
  `discount` decimal(7,2) DEFAULT NULL COMMENT '金额或者折扣',
  `start_time` datetime DEFAULT NULL COMMENT '有效开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '有效结束时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `overlay_use` int(7) NOT NULL DEFAULT '0' COMMENT '叠加使用限制(目前不做)',
  `special_date` varchar(255) DEFAULT NULL COMMENT '特殊日期(预留)',
  `rank_ids` varchar(255) DEFAULT NULL COMMENT '会员等级ids集(会员等级限制使用，预留)',
  `limit_store` varchar(255) DEFAULT NULL COMMENT '预留 门店限制',
  `create_time` datetime DEFAULT NULL,
  `issue_type` bit(1) DEFAULT b'0' COMMENT '发放方式 0：自动发送，1：手动发送',
  `status` int(1) DEFAULT NULL COMMENT '使用状态：-1已过期、0已使用、1未使用	',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_voucher_code
-- ----------------------------
DROP TABLE IF EXISTS `t_voucher_code`;
CREATE TABLE `t_voucher_code` (
  `code` varchar(36) NOT NULL,
  `use_time` datetime DEFAULT NULL,
  `voucher_id` varchar(36) NOT NULL,
  `balance` int(11) DEFAULT NULL COMMENT '余额',
  `status` int(1) NOT NULL COMMENT '0:无效  1：有效',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_voucher_info
-- ----------------------------
DROP TABLE IF EXISTS `t_voucher_info`;
CREATE TABLE `t_voucher_info` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '券类名称',
  `quantity` int(11) NOT NULL COMMENT '发放数量',
  `amount` decimal(6,0) DEFAULT NULL COMMENT '面额',
  `store_id` varchar(36) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间\r\n',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
