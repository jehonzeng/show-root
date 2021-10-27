/*
 Navicat Premium Data Transfer

 Source Server         : 测试（120.77.102.6）
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 120.77.102.6:3307
 Source Schema         : db_member

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/10/2021 09:54:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity_info
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_info`;
CREATE TABLE `t_activity_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) NOT NULL COMMENT '活动主题',
  `code` varchar(255) NOT NULL COMMENT '活动标识',
  `type` int(1) NOT NULL COMMENT '广告类型（0：跳转 1：充值）',
  `rule_id` varchar(36) DEFAULT NULL COMMENT '广告类型为1时，选择充值模板',
  `store_id` varchar(36) DEFAULT NULL COMMENT '门店id，存在时则限制门店',
  `start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '活动截止时间',
  `award_url` varchar(150) DEFAULT NULL COMMENT '跳转地址链接',
  `image_path` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `creator` varchar(36) DEFAULT NULL,
  `modifier` varchar(36) DEFAULT NULL,
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '活动状态0 失效 1有效',
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `unique` (`theme`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动信息表';

-- ----------------------------
-- Table structure for t_dishes_image
-- ----------------------------
DROP TABLE IF EXISTS `t_dishes_image`;
CREATE TABLE `t_dishes_image` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `dishes_id` varchar(36) NOT NULL COMMENT '菜品ID',
  `stage_id` varchar(36) DEFAULT NULL COMMENT '阶段ID',
  `stage_image` varchar(50) DEFAULT NULL COMMENT '阶段图片',
  `receive_id` varchar(36) DEFAULT NULL COMMENT '领取详细表ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_dishes_info
-- ----------------------------
DROP TABLE IF EXISTS `t_dishes_info`;
CREATE TABLE `t_dishes_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `dishes_name` varchar(50) NOT NULL COMMENT '菜品名称',
  `description` varchar(255) DEFAULT NULL COMMENT '菜品描述',
  `template_id` varchar(36) DEFAULT NULL COMMENT '菜品券ID',
  `days` int(1) DEFAULT NULL COMMENT '天数',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '使用状态（0：停用  1：启用）',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_dishes_stage
-- ----------------------------
DROP TABLE IF EXISTS `t_dishes_stage`;
CREATE TABLE `t_dishes_stage` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `dishes_id` varchar(36) DEFAULT NULL COMMENT '菜品ID',
  `stage` varchar(20) DEFAULT NULL COMMENT '阶段',
  `beginDays` int(3) DEFAULT NULL COMMENT '开始天数',
  `endDays` int(3) DEFAULT NULL COMMENT '结束天数',
  `sort` int(1) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_exchange_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_exchange_detail`;
CREATE TABLE `t_exchange_detail` (
  `mark_id` varchar(36) NOT NULL,
  `exchange_id` varchar(36) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `employee_id` varchar(36) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_give_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_give_ticket`;
CREATE TABLE `t_give_ticket` (
  `give_id` varchar(36) NOT NULL COMMENT '赠送券id',
  `template_id` varchar(36) NOT NULL COMMENT '券模板id',
  `quantity` int(11) DEFAULT '1' COMMENT '数量',
  PRIMARY KEY (`give_id`,`template_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_grade_record
-- ----------------------------
DROP TABLE IF EXISTS `t_grade_record`;
CREATE TABLE `t_grade_record` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `member_id` varchar(36) DEFAULT NULL COMMENT '会员id',
  `indent_id` varchar(36) DEFAULT NULL COMMENT '订单id',
  `consume_amount` decimal(20,2) DEFAULT NULL COMMENT '消费金额（成长值）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_grade_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_grade_ticket`;
CREATE TABLE `t_grade_ticket` (
  `grade_id` varchar(36) NOT NULL COMMENT '会员等级id',
  `template_id` varchar(36) NOT NULL COMMENT '优惠券id',
  `quantity` int(5) DEFAULT '1' COMMENT '数量',
  PRIMARY KEY (`grade_id`,`template_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_integral_account
-- ----------------------------
DROP TABLE IF EXISTS `t_integral_account`;
CREATE TABLE `t_integral_account` (
  `mark_id` varchar(36) NOT NULL,
  `account_no` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) NOT NULL,
  `total_integral` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分账户表';

-- ----------------------------
-- Table structure for t_integral_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_integral_detail`;
CREATE TABLE `t_integral_detail` (
  `mark_id` varchar(36) NOT NULL,
  `account_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `type` int(255) NOT NULL DEFAULT '1' COMMENT '-1 消费  0 赠送 1 评价所得 2 消费退回',
  `integral_limit` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` int(2) DEFAULT '1' COMMENT '1  有效  0 无效 -1 已过期',
  `old_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分记录表';

-- ----------------------------
-- Table structure for t_integral_exchange
-- ----------------------------
DROP TABLE IF EXISTS `t_integral_exchange`;
CREATE TABLE `t_integral_exchange` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `commodity_id` varchar(36) DEFAULT NULL COMMENT '菜品id',
  `template_id` varchar(36) DEFAULT NULL COMMENT '券id',
  `img_id` varchar(36) DEFAULT NULL COMMENT '图片id',
  `type` varchar(10) DEFAULT NULL COMMENT '展示类型',
  `consume_integral` int(11) DEFAULT NULL COMMENT '所需的积分',
  `ticket_quantity` int(11) DEFAULT '1' COMMENT '兑换券的数量',
  `exchange_quantity` int(11) DEFAULT NULL COMMENT '可兑换的数量',
  `start_time` datetime DEFAULT NULL COMMENT '可兑换开始的时间',
  `end_time` datetime DEFAULT NULL COMMENT '可兑换结束的时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `status` bit(1) NOT NULL COMMENT '状态（0：停用 1：启用）',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_integral_expire
-- ----------------------------
DROP TABLE IF EXISTS `t_integral_expire`;
CREATE TABLE `t_integral_expire` (
  `mark_id` varchar(36) NOT NULL,
  `expire_time` int(5) DEFAULT NULL COMMENT '过期时间（以月为单位）',
  `push_days` int(5) DEFAULT NULL COMMENT '提前推送的天数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_chance
-- ----------------------------
DROP TABLE IF EXISTS `t_match_chance`;
CREATE TABLE `t_match_chance` (
  `user_id` varchar(36) NOT NULL,
  `match_id` varchar(36) DEFAULT NULL,
  `total_count` int(11) NOT NULL DEFAULT '0' COMMENT '总共可以投票的次数',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用的投票数',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_exchange
-- ----------------------------
DROP TABLE IF EXISTS `t_match_exchange`;
CREATE TABLE `t_match_exchange` (
  `mark_id` varchar(36) NOT NULL,
  `match_id` varchar(36) NOT NULL COMMENT '竞赛id',
  `user_id` varchar(36) DEFAULT NULL,
  `exchange_total` int(11) DEFAULT NULL COMMENT '总兑换',
  `exchanged` int(11) DEFAULT NULL COMMENT '已经兑换',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_info
-- ----------------------------
DROP TABLE IF EXISTS `t_match_info`;
CREATE TABLE `t_match_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `match_name` varchar(50) NOT NULL COMMENT '竞赛名称',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建人',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `img_id` varchar(36) DEFAULT NULL COMMENT '导航图片',
  `description_id` varchar(36) DEFAULT NULL COMMENT '竞赛描述图片',
  `ticket_type` int(1) DEFAULT NULL COMMENT '0：线下券   1：线上券',
  `ticket_name` varchar(50) NOT NULL COMMENT '套票名称',
  `amount` decimal(6,2) DEFAULT NULL COMMENT '套票金额',
  `win_value` int(11) DEFAULT NULL COMMENT '套票增长值（对t_match_ticket的每张优惠券）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '竞赛活动备注',
  `give_chance` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否自动赠送机会0：不赠送 1：赠送',
  `status` bit(1) NOT NULL COMMENT '竞赛活动状态0：无效 1：有效',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='竞赛活动表';

-- ----------------------------
-- Table structure for t_match_item
-- ----------------------------
DROP TABLE IF EXISTS `t_match_item`;
CREATE TABLE `t_match_item` (
  `match_id` varchar(36) NOT NULL,
  `team_id` varchar(36) NOT NULL,
  PRIMARY KEY (`match_id`,`team_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_match_pay`;
CREATE TABLE `t_match_pay` (
  `mark_id` varchar(36) NOT NULL,
  `match_id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL COMMENT '总额',
  `quantity` int(11) NOT NULL COMMENT '购买数量',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` bit(1) DEFAULT NULL COMMENT '支付状态',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_prize
-- ----------------------------
DROP TABLE IF EXISTS `t_match_prize`;
CREATE TABLE `t_match_prize` (
  `mark_id` varchar(36) NOT NULL,
  `stage_id` varchar(36) NOT NULL,
  `prize_name` varchar(50) DEFAULT NULL,
  `img_id` varchar(36) DEFAULT NULL,
  `template_id` varchar(36) DEFAULT NULL COMMENT '券id (可有可无）',
  `sort` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_receive
-- ----------------------------
DROP TABLE IF EXISTS `t_match_receive`;
CREATE TABLE `t_match_receive` (
  `mark_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `prize_id` varchar(36) NOT NULL COMMENT 't_match_prize中的主键',
  `quantity` int(11) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_result
-- ----------------------------
DROP TABLE IF EXISTS `t_match_result`;
CREATE TABLE `t_match_result` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `stage_id` varchar(36) NOT NULL COMMENT '赛程阶段id',
  `team_id` varchar(36) NOT NULL COMMENT '赛程选手id',
  `last_time` datetime NOT NULL COMMENT '当前赛程最后一场比赛开始的时间（该阶段用户最后的投票时间）',
  `team_status` int(1) DEFAULT NULL COMMENT '队伍参与当前赛程的状态（-1：淘汰  0：进行中 1：晋级）',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='赛程选手表';

-- ----------------------------
-- Table structure for t_match_stage
-- ----------------------------
DROP TABLE IF EXISTS `t_match_stage`;
CREATE TABLE `t_match_stage` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `match_id` varchar(36) NOT NULL COMMENT '竞赛id',
  `stage_name` varchar(255) DEFAULT NULL COMMENT '阶段名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` bit(1) DEFAULT NULL COMMENT '状态0：无效 1：有效',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='赛程阶段表';

-- ----------------------------
-- Table structure for t_match_team
-- ----------------------------
DROP TABLE IF EXISTS `t_match_team`;
CREATE TABLE `t_match_team` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `team_name` varchar(50) NOT NULL COMMENT '名称',
  `img_id` varchar(36) DEFAULT NULL COMMENT '图片id',
  `sort` int(4) DEFAULT NULL COMMENT '序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL COMMENT '状态0：无效 1：有效',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_match_ticket`;
CREATE TABLE `t_match_ticket` (
  `mark_id` varchar(36) NOT NULL,
  `match_id` varchar(36) DEFAULT NULL,
  `template_id` varchar(36) DEFAULT NULL COMMENT '对应t_ticket_template的主键',
  `quantity` int(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_match_vote
-- ----------------------------
DROP TABLE IF EXISTS `t_match_vote`;
CREATE TABLE `t_match_vote` (
  `mark_id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `team_id` varchar(36) DEFAULT NULL COMMENT '队伍id',
  `quantity` int(3) DEFAULT NULL COMMENT '数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `stage_id` varchar(36) DEFAULT NULL COMMENT '参与的赛程阶段',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='会员竞赛表';

-- ----------------------------
-- Table structure for t_member_account
-- ----------------------------
DROP TABLE IF EXISTS `t_member_account`;
CREATE TABLE `t_member_account` (
  `mark_id` varchar(36) NOT NULL,
  `account_no` varchar(20) NOT NULL COMMENT '会员号',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户Id',
  `grade_id` varchar(30) DEFAULT NULL COMMENT '等级',
  `name` varchar(50) DEFAULT NULL COMMENT '注册姓名',
  `gender` int(1) DEFAULT NULL COMMENT '0：未知 1:男士 2：女士',
  `phone` varchar(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `total_amount` decimal(9,2) DEFAULT NULL COMMENT '总额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`),
  UNIQUE KEY `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员账户表';

-- ----------------------------
-- Table structure for t_member_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_member_activity`;
CREATE TABLE `t_member_activity` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '活动名称',
  `code` varchar(50) DEFAULT NULL COMMENT '活动编号',
  `type` varchar(50) DEFAULT NULL COMMENT '活动类型',
  `img_id` varchar(36) DEFAULT NULL COMMENT '活动图片',
  `store_id` varchar(36) DEFAULT NULL COMMENT '门店id',
  `remark` varchar(255) DEFAULT NULL COMMENT '活动备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '活动状态（0：失效 1：有效）',
  `days` int(2) DEFAULT NULL COMMENT '天数',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='会员活动';

-- ----------------------------
-- Table structure for t_member_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_member_detail`;
CREATE TABLE `t_member_detail` (
  `mark_id` varchar(36) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL COMMENT '充值赠送记录充值记录的id',
  `account_id` varchar(36) NOT NULL,
  `amount` decimal(9,2) DEFAULT NULL COMMENT '金额（充值或者消费）',
  `creator` varchar(36) DEFAULT NULL COMMENT '线下操作人员',
  `create_time` datetime DEFAULT NULL COMMENT '充值或者消费或者撤回时间',
  `store_id` varchar(100) DEFAULT NULL COMMENT '门店名称',
  `type` int(1) NOT NULL COMMENT '-1 消费  0  赠送  1 充值 2 退款',
  `remark` varchar(255) DEFAULT NULL COMMENT '记录说明',
  `indent_id` varchar(36) DEFAULT NULL COMMENT '订单支付会退款时操作',
  `surplus_amount` decimal(9,2) DEFAULT NULL COMMENT '余额',
  `status` int(1) DEFAULT '1' COMMENT '-1 已撤回  0 操作待确认  1有效状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值消费记录表';

-- ----------------------------
-- Table structure for t_member_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_member_grade`;
CREATE TABLE `t_member_grade` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键id',
  `grade_name` varchar(50) DEFAULT NULL COMMENT '等级名',
  `image_id` varchar(36) DEFAULT NULL COMMENT '等级图片',
  `consume_total` int(15) DEFAULT NULL COMMENT '成长值（所需消费）',
  `give_type` int(1) DEFAULT NULL COMMENT '赠送的类型（0：两者都没有 1：积分 2：优惠券 3：两者都有）',
  `give_integral` int(9) DEFAULT NULL COMMENT '等级升级赠送的积分',
  `integral_proportion` decimal(9,2) DEFAULT NULL COMMENT '消费赠送积分',
  `member_discount` decimal(9,2) DEFAULT NULL COMMENT '会员折扣',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(1) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_member_lottery
-- ----------------------------
DROP TABLE IF EXISTS `t_member_lottery`;
CREATE TABLE `t_member_lottery` (
  `mark_id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `consume_integral` int(3) DEFAULT NULL COMMENT '消费的积分',
  `multiple` int(8) DEFAULT '100' COMMENT '概率倍数',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `creator` varchar(50) DEFAULT NULL COMMENT '修改人',
  `status` bit(1) NOT NULL COMMENT '状态（0：禁用 1：启用）',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_pay_back
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_back`;
CREATE TABLE `t_pay_back` (
  `mark_id` varchar(36) NOT NULL,
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '1：会员充值 2：竞赛付款 ',
  `pay_id` varchar(50) NOT NULL COMMENT '支付提交到微信支付参数中的id',
  `rule_id` varchar(36) DEFAULT NULL COMMENT '充值模板id',
  `back_info` varchar(255) DEFAULT NULL,
  `back_type` int(11) NOT NULL DEFAULT '0' COMMENT '-1 失败 1 成功',
  `add_time` datetime NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `code` varchar(36) DEFAULT NULL COMMENT '充值人小程序openid',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_pend_dishes
-- ----------------------------
DROP TABLE IF EXISTS `t_pend_dishes`;
CREATE TABLE `t_pend_dishes` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  `indent_id` varchar(36) DEFAULT NULL COMMENT '订单id',
  `activity_id` varchar(36) DEFAULT NULL COMMENT '活动ID',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '领取状态（0：已失效 1：待领取 2：已领取）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_prize_info
-- ----------------------------
DROP TABLE IF EXISTS `t_prize_info`;
CREATE TABLE `t_prize_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键id',
  `img_id` varchar(36) DEFAULT NULL COMMENT '奖品图片',
  `lottery_id` varchar(36) DEFAULT NULL COMMENT '抽奖活动id',
  `prize_name` varchar(50) NOT NULL COMMENT '奖品名称',
  `prize_type` int(2) NOT NULL COMMENT '奖品类型(0谢谢惠顾，1积分，2优惠券)',
  `integral` int(9) DEFAULT NULL COMMENT '抽奖赠送的积分',
  `template_id` varchar(36) DEFAULT NULL COMMENT '抽奖赠送的优惠券',
  `probability` decimal(10,8) DEFAULT NULL COMMENT '中奖概率 例如 (0.05)',
  `quantity` int(4) DEFAULT NULL COMMENT '库存数量',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '使用状态（0：停用 1：启用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='奖品信息表';

-- ----------------------------
-- Table structure for t_prize_receive
-- ----------------------------
DROP TABLE IF EXISTS `t_prize_receive`;
CREATE TABLE `t_prize_receive` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键id',
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `prize_id` varchar(36) DEFAULT NULL COMMENT '奖品id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间/领取时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='奖品领取表';

-- ----------------------------
-- Table structure for t_receive_dishes
-- ----------------------------
DROP TABLE IF EXISTS `t_receive_dishes`;
CREATE TABLE `t_receive_dishes` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '认领人',
  `pend_id` varchar(36) NOT NULL COMMENT '待领取ID',
  `dishes_id` varchar(36) NOT NULL COMMENT '菜品ID',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户ID',
  `adopted_name` varchar(40) DEFAULT NULL COMMENT '领养昵称',
  `stage_id` varchar(36) DEFAULT NULL COMMENT '阶段ID',
  `code` varchar(36) DEFAULT NULL COMMENT '编号',
  `status` int(11) DEFAULT '0' COMMENT '状态（0：已失效  1：成长中 2：可领取券  3：已领取券 ）',
  `receive_time` datetime NOT NULL COMMENT '领养时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='领取详细记录表';

-- ----------------------------
-- Table structure for t_receive_record
-- ----------------------------
DROP TABLE IF EXISTS `t_receive_record`;
CREATE TABLE `t_receive_record` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  `description` varchar(255) NOT NULL COMMENT '动态描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='领取记录表';

-- ----------------------------
-- Table structure for t_recharge_refund
-- ----------------------------
DROP TABLE IF EXISTS `t_recharge_refund`;
CREATE TABLE `t_recharge_refund` (
  `mark_id` varchar(36) NOT NULL,
  `detail_id` varchar(50) NOT NULL COMMENT '充值ID',
  `refund_no` varchar(50) NOT NULL COMMENT '退款号',
  `refund_status` int(1) NOT NULL COMMENT '0：申请失败 1：申请成功',
  `total_fee` decimal(6,2) NOT NULL COMMENT '退款金额',
  `create_time` datetime NOT NULL,
  `refund_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_recharge_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_recharge_rule`;
CREATE TABLE `t_recharge_rule` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) NOT NULL COMMENT '规则名称',
  `rule_type` int(11) DEFAULT '0' COMMENT '充值类型(0 普通充值  1 订单金额倍数充值)',
  `times` int(11) DEFAULT NULL COMMENT '充值倍数',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `limit_amount` decimal(10,2) DEFAULT NULL COMMENT '充值额度/赠送门槛',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '赠送类型 0 金额 1 积分 2 代金券',
  `amount` decimal(9,2) DEFAULT NULL COMMENT '金额或积分',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `creator` varchar(36) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态-1删除 0 无效 1有效',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_recharge_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_recharge_ticket`;
CREATE TABLE `t_recharge_ticket` (
  `rule_id` varchar(36) NOT NULL COMMENT '关联充值活动id',
  `template_id` varchar(36) NOT NULL COMMENT '关联券模板id',
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`rule_id`,`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_sign_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_sign_detail`;
CREATE TABLE `t_sign_detail` (
  `mark_id` varchar(36) NOT NULL COMMENT ' 主键',
  `sign_id` varchar(36) DEFAULT NULL COMMENT '签到id',
  `rule_id` varchar(36) DEFAULT NULL COMMENT '规则id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_sign_member
-- ----------------------------
DROP TABLE IF EXISTS `t_sign_member`;
CREATE TABLE `t_sign_member` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `year` int(4) DEFAULT NULL COMMENT '年份',
  `month` int(2) DEFAULT NULL COMMENT '月份',
  `continue_sign` int(11) DEFAULT '0' COMMENT '连续签到数',
  `sign` varchar(31) DEFAULT NULL COMMENT '签到',
  `sign_time` datetime DEFAULT NULL,
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_sign_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_sign_rule`;
CREATE TABLE `t_sign_rule` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键id',
  `name` varchar(50) DEFAULT NULL COMMENT '规则名',
  `img_id` varchar(36) DEFAULT NULL COMMENT '图片id',
  `give_type` int(2) DEFAULT NULL COMMENT '赠送类型(1积分，2优惠券)',
  `give_integral` int(5) DEFAULT NULL COMMENT '赠送的积分',
  `template_id` varchar(36) DEFAULT NULL COMMENT '赠送的券',
  `days` int(2) DEFAULT NULL COMMENT '天数(签到满足天数即可领取礼包)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` bit(1) DEFAULT b'1' COMMENT '状态',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_ticket_exchange
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket_exchange`;
CREATE TABLE `t_ticket_exchange` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键id',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `exchange_id` varchar(36) DEFAULT NULL COMMENT 't_integarl_exchange的主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
