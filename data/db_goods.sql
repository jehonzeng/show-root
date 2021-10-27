/*
 Navicat Premium Data Transfer

 Source Server         : 测试（120.77.102.6）
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 120.77.102.6:3307
 Source Schema         : db_goods

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 27/10/2021 09:53:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_accessory_info
-- ----------------------------
DROP TABLE IF EXISTS `t_accessory_info`;
CREATE TABLE `t_accessory_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL COMMENT '附属品名称',
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '附属品类型：0自购 1赠送',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '显示状态(0下架，1上架）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `base_price` decimal(6,2) DEFAULT NULL COMMENT '成本',
  `sale_price` decimal(6,2) DEFAULT NULL COMMENT '售价',
  `description` varchar(255) DEFAULT NULL COMMENT '附属品描述',
  `stock_size` int(11) DEFAULT NULL COMMENT '库存量',
  `image_path` varchar(50) DEFAULT NULL COMMENT '图片',
  `sort` int(11) NOT NULL DEFAULT '20',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附属品信息表';

-- ----------------------------
-- Table structure for t_brand_info
-- ----------------------------
DROP TABLE IF EXISTS `t_brand_info`;
CREATE TABLE `t_brand_info` (
  `mark_id` varchar(36) NOT NULL,
  `cn_name` varchar(20) DEFAULT NULL COMMENT '品牌中文名称',
  `en_name` varchar(50) DEFAULT NULL COMMENT '品牌英文名称',
  `brand_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '显示状态',
  `description` varchar(255) DEFAULT NULL COMMENT '品牌描述',
  `brand_url` varchar(150) DEFAULT NULL COMMENT '品牌官方网址',
  `brand_logo` varchar(20) DEFAULT NULL COMMENT '关联LOGO图像',
  `reg_time` date DEFAULT NULL COMMENT '创建时间',
  `owner_name` varchar(50) DEFAULT NULL COMMENT '拥有者称呼',
  `server_type` varchar(4) DEFAULT NULL COMMENT '合作类型',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='品牌表';

-- ----------------------------
-- Table structure for t_category_info
-- ----------------------------
DROP TABLE IF EXISTS `t_category_info`;
CREATE TABLE `t_category_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `super_id` varchar(36) DEFAULT NULL COMMENT '上级分类',
  `name` varchar(20) DEFAULT NULL COMMENT '分类名称',
  `level` int(7) DEFAULT '0' COMMENT '类别级别（0顶级类 1 一级 2二级，3.....)',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态(0无效，1有效）',
  `sort` int(11) DEFAULT '20' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- ----------------------------
-- Table structure for t_column_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_column_goods`;
CREATE TABLE `t_column_goods` (
  `mark_id` varchar(36) NOT NULL,
  `column_id` varchar(36) NOT NULL,
  `goods_id` varchar(36) NOT NULL,
  `goods_type` int(1) NOT NULL DEFAULT '0' COMMENT '商品类型 0:普通商品 2:套餐商品',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '首页栏目商品0不显示 1显示',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '栏目商品显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='栏目商品表';

-- ----------------------------
-- Table structure for t_column_info
-- ----------------------------
DROP TABLE IF EXISTS `t_column_info`;
CREATE TABLE `t_column_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL COMMENT '栏目主题',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '栏目显示状态（0不显示 1显示）',
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '栏目类型 0：单品 2：套餐',
  `image_path` varchar(36) DEFAULT NULL COMMENT '关联栏目显示图片',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '排序(降序排列)',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='栏目信息表';

-- ----------------------------
-- Table structure for t_cook_certified
-- ----------------------------
DROP TABLE IF EXISTS `t_cook_certified`;
CREATE TABLE `t_cook_certified` (
  `mark_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL COMMENT '关联用户',
  `short_name` varchar(20) DEFAULT NULL COMMENT '称呼',
  `cook_style` varchar(50) DEFAULT NULL COMMENT '擅长菜系集',
  `cook_level` varchar(4) DEFAULT NULL COMMENT '厨师等级',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
  `province` varchar(10) DEFAULT NULL COMMENT '省',
  `city` varchar(10) DEFAULT NULL COMMENT '市',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `certified` bit(1) DEFAULT b'0' COMMENT '认证否',
  `image_path` varchar(36) DEFAULT NULL COMMENT '厨师头像',
  `description` text COMMENT '厨师详情介绍',
  `personal_signature` varchar(255) DEFAULT NULL COMMENT '个性签名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='厨师认证表';

-- ----------------------------
-- Table structure for t_cook_follow
-- ----------------------------
DROP TABLE IF EXISTS `t_cook_follow`;
CREATE TABLE `t_cook_follow` (
  `user_id` varchar(36) NOT NULL,
  `cook_id` varchar(36) NOT NULL,
  `follow` int(1) DEFAULT '0' COMMENT '关注（0：取关 1：关注）',
  PRIMARY KEY (`user_id`,`cook_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_delivery_area
-- ----------------------------
DROP TABLE IF EXISTS `t_delivery_area`;
CREATE TABLE `t_delivery_area` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `storehouse_id` varchar(36) NOT NULL COMMENT '关联仓库',
  `province` varchar(10) DEFAULT NULL COMMENT '省',
  `city` varchar(10) DEFAULT NULL COMMENT '市',
  `area` varchar(10) DEFAULT NULL COMMENT '区',
  `limit_price` decimal(6,2) DEFAULT NULL COMMENT '满额免邮',
  `delivery_price` decimal(6,2) DEFAULT NULL COMMENT '配送费',
  `creator` varchar(36) DEFAULT NULL COMMENT '添加人',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库配送范围表';

-- ----------------------------
-- Table structure for t_food_info
-- ----------------------------
DROP TABLE IF EXISTS `t_food_info`;
CREATE TABLE `t_food_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `food_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态 0未使用 1使用 ',
  `purchase_rate` decimal(8,2) NOT NULL DEFAULT '1.00' COMMENT '采购比例（采购量/使用量）（>=1.0）',
  `unit` varchar(4) DEFAULT NULL COMMENT '单位',
  `image_path` varchar(36) DEFAULT NULL COMMENT '食材图标',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食材表';

-- ----------------------------
-- Table structure for t_food_item
-- ----------------------------
DROP TABLE IF EXISTS `t_food_item`;
CREATE TABLE `t_food_item` (
  `mark_id` varchar(36) NOT NULL,
  `food_id` varchar(36) NOT NULL DEFAULT '' COMMENT '关联食材',
  `goods_id` varchar(36) NOT NULL COMMENT '关联菜品',
  `specification_ids` varchar(255) DEFAULT NULL COMMENT '规格集',
  `use_size` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '实际使用量',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '启用状态 0不启用  1启用',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品食材中间表';

-- ----------------------------
-- Table structure for t_goods_content
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_content`;
CREATE TABLE `t_goods_content` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `goods_id` varchar(36) NOT NULL COMMENT '关联商品',
  `content` text COMMENT '商品介绍详情',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品详情介绍表';

-- ----------------------------
-- Table structure for t_goods_image
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_image`;
CREATE TABLE `t_goods_image` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `goods_id` varchar(36) DEFAULT NULL COMMENT '关联商品',
  `specification_ids` varchar(255) DEFAULT NULL COMMENT '关联属性集',
  `image_path` varchar(36) DEFAULT NULL COMMENT '服务器相对地址',
  `server_type` int(1) DEFAULT NULL COMMENT '图片类别( 0:小展示图  1:大展示图 2:规格图)',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片表';

-- ----------------------------
-- Table structure for t_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_info`;
CREATE TABLE `t_goods_info` (
  `mark_id` varchar(36) NOT NULL,
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `description` varchar(150) DEFAULT NULL COMMENT '商品描述',
  `server_status` varchar(10) DEFAULT NULL COMMENT '显示状态（查询动态属性状态）',
  `cook_style` varchar(10) DEFAULT NULL COMMENT '菜系（查询动态属性状态）',
  `server_type` int(1) DEFAULT NULL COMMENT '供货类型（0自营1非自营）',
  `base_price` decimal(6,2) DEFAULT NULL COMMENT '成本价',
  `sale_price` decimal(6,2) DEFAULT NULL COMMENT '基本售价',
  `unit` varchar(10) DEFAULT NULL COMMENT '单位（例如：克、个）',
  `upper_time` datetime DEFAULT NULL COMMENT '手动上架时间',
  `down_time` datetime DEFAULT NULL COMMENT '手动下架时间',
  `pre_upper_time` datetime DEFAULT NULL COMMENT '预上架时间',
  `pre_down_time` datetime DEFAULT NULL COMMENT '预下架时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(36) DEFAULT NULL COMMENT '修改人',
  `category_id` varchar(36) DEFAULT NULL COMMENT '关联分类',
  `type_id` varchar(36) DEFAULT NULL COMMENT '关联商品类型（供选择规格，不做其他使用）',
  `brand_id` varchar(36) DEFAULT NULL COMMENT '关联品牌',
  `cooker` varchar(36) DEFAULT NULL COMMENT '关联厨师',
  `sort` int(11) NOT NULL DEFAULT '20',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品基础表spu';

-- ----------------------------
-- Table structure for t_goods_judge
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_judge`;
CREATE TABLE `t_goods_judge` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `order_id` varchar(36) DEFAULT NULL COMMENT '关联订单',
  `goods_id` varchar(36) NOT NULL COMMENT '关联库存商品',
  `specification_ids` varchar(200) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL COMMENT '关联评论人',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '显示状态',
  `description` varchar(255) DEFAULT NULL COMMENT '评价内容',
  `commentator` varchar(36) DEFAULT NULL COMMENT '评论人昵称',
  `add_time` datetime DEFAULT NULL COMMENT '评论时间',
  `star` int(1) NOT NULL DEFAULT '5' COMMENT '评价星级',
  `sort` int(11) NOT NULL DEFAULT '99' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评价表';

-- ----------------------------
-- Table structure for t_goods_server
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_server`;
CREATE TABLE `t_goods_server` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `goods_id` varchar(36) NOT NULL COMMENT '关联服务支持',
  `server_id` varchar(36) NOT NULL COMMENT '关联商品',
  `sort` int(11) NOT NULL DEFAULT '99' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品服务支持表';

-- ----------------------------
-- Table structure for t_goods_specification
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_specification`;
CREATE TABLE `t_goods_specification` (
  `mark_id` varchar(36) NOT NULL,
  `goods_id` varchar(36) NOT NULL COMMENT '关联商品',
  `specification_ids` varchar(200) NOT NULL COMMENT '关联属性集合',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  `base_price` decimal(6,2) NOT NULL COMMENT '成本价',
  `sale_price` decimal(6,2) NOT NULL COMMENT '售价',
  `goods_no` varchar(20) DEFAULT NULL COMMENT '商品编码',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_goods_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_stock`;
CREATE TABLE `t_goods_stock` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `storehouse_id` varchar(36) NOT NULL COMMENT '关联仓库',
  `goods_id` varchar(36) DEFAULT NULL COMMENT '商品id字段',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '商品与规格组信息id(t_goods_specification的主键)',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  `total_stock` int(11) NOT NULL COMMENT '总库存',
  `current_stock` int(11) NOT NULL COMMENT '当前库存',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存商品表sku';

-- ----------------------------
-- Table structure for t_goods_type
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_type`;
CREATE TABLE `t_goods_type` (
  `mark_id` varchar(36) NOT NULL,
  `type_name` varchar(50) DEFAULT NULL,
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  `sort` int(11) NOT NULL DEFAULT '20',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品类型表';

-- ----------------------------
-- Table structure for t_goods_voucher
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_voucher`;
CREATE TABLE `t_goods_voucher` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `product_id` varchar(36) NOT NULL COMMENT '关联商品',
  `specification_ids` varchar(255) DEFAULT NULL COMMENT '规格集',
  `product_type` int(1) NOT NULL DEFAULT '0' COMMENT '0 普通商品 1套餐商品',
  `voucher_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `price` decimal(6,2) DEFAULT NULL COMMENT '价格',
  `stock` int(11) DEFAULT NULL,
  `current_stock` int(11) DEFAULT NULL,
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态(0无效1有效)',
  `sort` int(11) NOT NULL DEFAULT '99' COMMENT '显示顺序',
  `voucher_no` varchar(20) DEFAULT NULL COMMENT '商品券编码',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品券表';

-- ----------------------------
-- Table structure for t_icon_info
-- ----------------------------
DROP TABLE IF EXISTS `t_icon_info`;
CREATE TABLE `t_icon_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL,
  `image_path` varchar(36) DEFAULT NULL COMMENT '关联图标图像',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '图标显示（0不显示 1显示）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图标信息表';

-- ----------------------------
-- Table structure for t_icon_item
-- ----------------------------
DROP TABLE IF EXISTS `t_icon_item`;
CREATE TABLE `t_icon_item` (
  `mark_id` varchar(36) NOT NULL,
  `goods_id` varchar(36) DEFAULT NULL,
  `icon_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图标商品表';

-- ----------------------------
-- Table structure for t_label_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_label_goods`;
CREATE TABLE `t_label_goods` (
  `mark_id` varchar(36) NOT NULL,
  `goods_id` varchar(36) NOT NULL,
  `label_id` varchar(36) NOT NULL,
  `server_status` bit(1) DEFAULT b'0' COMMENT '显示状态（0不显示1显示）',
  `goods_type` int(1) NOT NULL DEFAULT '0' COMMENT '类型：0 普通商品 2套餐商品',
  `sort` int(11) NOT NULL DEFAULT '20',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品标签分类表';

-- ----------------------------
-- Table structure for t_label_info
-- ----------------------------
DROP TABLE IF EXISTS `t_label_info`;
CREATE TABLE `t_label_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL COMMENT '分类标签主题',
  `server_type` int(1) NOT NULL DEFAULT '0' COMMENT '标签类型（0 单品 2套餐）',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '显示状态（0 不显示1显示）',
  `image_path` varchar(36) DEFAULT NULL COMMENT '关联分类标签图片',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类标签信息表';

-- ----------------------------
-- Table structure for t_meal_content
-- ----------------------------
DROP TABLE IF EXISTS `t_meal_content`;
CREATE TABLE `t_meal_content` (
  `mark_id` varchar(36) NOT NULL,
  `content` text,
  `meal_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐图文详情表';

-- ----------------------------
-- Table structure for t_meal_image
-- ----------------------------
DROP TABLE IF EXISTS `t_meal_image`;
CREATE TABLE `t_meal_image` (
  `mark_id` varchar(36) NOT NULL,
  `meal_id` varchar(36) DEFAULT NULL,
  `server_type` int(1) DEFAULT NULL COMMENT '图片类别( 0:小展示图  1:大展示图）',
  `image_path` varchar(36) DEFAULT NULL,
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐图片表';

-- ----------------------------
-- Table structure for t_meal_info
-- ----------------------------
DROP TABLE IF EXISTS `t_meal_info`;
CREATE TABLE `t_meal_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL COMMENT '套餐主题',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `base_price` decimal(6,2) DEFAULT NULL,
  `sale_price` decimal(6,2) DEFAULT NULL COMMENT '套餐售价',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '套餐显示状态（0下架 1上架）',
  `description` varchar(255) DEFAULT NULL COMMENT '套餐描述',
  `meal_no` varchar(20) DEFAULT NULL COMMENT '套餐编码',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐信息表';

-- ----------------------------
-- Table structure for t_meal_item
-- ----------------------------
DROP TABLE IF EXISTS `t_meal_item`;
CREATE TABLE `t_meal_item` (
  `mark_id` varchar(36) NOT NULL,
  `meal_id` varchar(36) DEFAULT NULL COMMENT '套餐商品',
  `goods_id` varchar(36) DEFAULT NULL COMMENT '关联商品',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '套餐单品数量',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '组合商品排序',
  `specification_ids` varchar(255) DEFAULT NULL COMMENT '关联规格集',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐商品表';

-- ----------------------------
-- Table structure for t_meal_judge
-- ----------------------------
DROP TABLE IF EXISTS `t_meal_judge`;
CREATE TABLE `t_meal_judge` (
  `mark_id` varchar(36) NOT NULL,
  `order_id` varchar(36) DEFAULT NULL,
  `meal_id` varchar(255) DEFAULT NULL COMMENT '关联套餐商品',
  `user_id` varchar(36) DEFAULT NULL COMMENT '关联评论人',
  `add_time` datetime DEFAULT NULL COMMENT '评论时间',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  `description` varchar(255) DEFAULT NULL,
  `commentator` varchar(36) DEFAULT NULL COMMENT '评论人昵称',
  `star` int(1) NOT NULL DEFAULT '5' COMMENT '评级',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '显示顺序',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐评论表\r\n';

-- ----------------------------
-- Table structure for t_meal_server
-- ----------------------------
DROP TABLE IF EXISTS `t_meal_server`;
CREATE TABLE `t_meal_server` (
  `mark_id` varchar(36) NOT NULL,
  `meal_id` varchar(36) DEFAULT NULL,
  `server_id` varchar(36) DEFAULT NULL,
  `sort` int(1) NOT NULL DEFAULT '99',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_meal_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_meal_stock`;
CREATE TABLE `t_meal_stock` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `storehouse_id` varchar(36) NOT NULL COMMENT '关联仓库',
  `meal_id` varchar(36) DEFAULT NULL COMMENT '套餐主键',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  `total_stock` int(11) NOT NULL COMMENT '总库存',
  `current_stock` int(11) NOT NULL COMMENT '当前库存',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存套餐表sku';

-- ----------------------------
-- Table structure for t_purchase_history
-- ----------------------------
DROP TABLE IF EXISTS `t_purchase_history`;
CREATE TABLE `t_purchase_history` (
  `mark_id` varchar(36) NOT NULL,
  `food_id` varchar(36) NOT NULL COMMENT '采购食材',
  `purchase_volume` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '采购量(每次）',
  `buy_time` datetime NOT NULL COMMENT '采购具体时间',
  `user_id` varchar(36) NOT NULL COMMENT '指派人',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购历史表';

-- ----------------------------
-- Table structure for t_purchase_info
-- ----------------------------
DROP TABLE IF EXISTS `t_purchase_info`;
CREATE TABLE `t_purchase_info` (
  `mark_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `food_id` varchar(36) NOT NULL COMMENT '关联食材',
  `purchase_total` decimal(8,2) DEFAULT NULL COMMENT '需采购的总量',
  `buy_total` decimal(8,2) DEFAULT NULL COMMENT '当前购买总量',
  `buy_time` date DEFAULT NULL COMMENT '采购日期',
  `server_status` int(1) NOT NULL DEFAULT '0' COMMENT '采购指派 ：0 未指派 1 已指派',
  `reflash_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_id` varchar(36) DEFAULT NULL COMMENT '采购人',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购信息表';

-- ----------------------------
-- Table structure for t_server_support
-- ----------------------------
DROP TABLE IF EXISTS `t_server_support`;
CREATE TABLE `t_server_support` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `theme` varchar(50) DEFAULT NULL COMMENT '主题',
  `detail_explain` text COMMENT '详情说明',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT 'f服务说明状态(0不显示1显示)',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务支持表';

-- ----------------------------
-- Table structure for t_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_shopping_cart`;
CREATE TABLE `t_shopping_cart` (
  `mark_id` varchar(36) NOT NULL,
  `product_id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `product_type` int(1) NOT NULL COMMENT '普通商品/菜品券(0单品,  1菜品券, 2套餐)',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `specification_ids` varchar(200) DEFAULT NULL COMMENT '商品规格集',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `add_price` decimal(6,2) NOT NULL COMMENT '用户添加到购物车时的商品价格',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `checked` bit(1) DEFAULT NULL COMMENT '是否勾选',
  `product_no` varchar(20) DEFAULT NULL COMMENT '商品编码',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_special_info
-- ----------------------------
DROP TABLE IF EXISTS `t_special_info`;
CREATE TABLE `t_special_info` (
  `mark_id` varchar(36) NOT NULL,
  `theme` varchar(50) DEFAULT NULL COMMENT '促销主题',
  `promotion_type` int(1) DEFAULT NULL COMMENT '关联活动类型（0结算满减类,1商品直减类 2加价购 3满件促销类）',
  `promotion_mode` int(1) DEFAULT NULL COMMENT '活动方式（0减额 1折扣）',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0失效1有效',
  `limit_price` decimal(6,2) DEFAULT NULL COMMENT '满减/满件',
  `price` decimal(6,2) DEFAULT NULL COMMENT '优惠额度',
  `discount` decimal(3,2) DEFAULT NULL COMMENT '优惠折扣(0.10-0.99)',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `user_type` int(1) DEFAULT NULL COMMENT '目标用户：0全部用户 1未下单用户 （预留暂未用）',
  `image_path` varchar(36) DEFAULT NULL COMMENT '促销标识图',
  `share_type` bit(1) DEFAULT NULL COMMENT '是否与其他活动共享 ：0不共享 1共享（预留暂未用）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='促销主表(变动商品价格类)';

-- ----------------------------
-- Table structure for t_special_item
-- ----------------------------
DROP TABLE IF EXISTS `t_special_item`;
CREATE TABLE `t_special_item` (
  `mark_id` varchar(36) NOT NULL,
  `special_id` varchar(36) DEFAULT NULL COMMENT '关联促销活动',
  `goods_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='促销副表(变动商品价格)';

-- ----------------------------
-- Table structure for t_specification_info
-- ----------------------------
DROP TABLE IF EXISTS `t_specification_info`;
CREATE TABLE `t_specification_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `attr_name` varchar(20) DEFAULT NULL COMMENT '属性名',
  `attr_value` varchar(20) DEFAULT NULL COMMENT '属性值',
  `server_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `sort` int(11) NOT NULL DEFAULT '20' COMMENT '排序（暂时没用到）',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格属性表';

-- ----------------------------
-- Table structure for t_storehouse_info
-- ----------------------------
DROP TABLE IF EXISTS `t_storehouse_info`;
CREATE TABLE `t_storehouse_info` (
  `mark_id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '仓库名称',
  `contact` varchar(36) DEFAULT NULL COMMENT '仓库联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '仓库电话',
  `area` varchar(20) DEFAULT NULL COMMENT '仓库区域',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `country` varchar(20) DEFAULT NULL COMMENT '国家',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `server_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`mark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库信息表';

-- ----------------------------
-- Table structure for t_type_specification
-- ----------------------------
DROP TABLE IF EXISTS `t_type_specification`;
CREATE TABLE `t_type_specification` (
  `type_id` varchar(36) NOT NULL,
  `specification_id` varchar(36) NOT NULL,
  `sort` int(11) DEFAULT '99' COMMENT '排序',
  `default_or` bit(1) DEFAULT NULL COMMENT '默认否',
  PRIMARY KEY (`type_id`,`specification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- View structure for v_cooker_sale_rank
-- ----------------------------
DROP VIEW IF EXISTS `v_cooker_sale_rank`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_cooker_sale_rank` AS select `c`.`cooker` AS `cooker`,sum(`c`.`quantity`) AS `quantity` from ((select `g`.`cooker` AS `cooker`,sum(`i`.`quantity`) AS `quantity` from (((select `g`.`product_id` AS `product_id`,sum(`g`.`quantity`) AS `quantity` from (select `i`.`product_id` AS `product_id`,sum(`i`.`quantity`) AS `quantity` from `db_order`.`t_order_item` `i` where (`i`.`product_type` = 0) group by `i`.`product_id` union all select `s`.`goods_id` AS `product_id`,sum(`s`.`quantity`) AS `quantity` from `db_order`.`t_seckill_order` `s` group by `s`.`goods_id` union all select `i`.`goods_id` AS `product_id`,sum(`i`.`quantity`) AS `quantity` from `db_order`.`t_teambuy_order` `i` group by `i`.`goods_id`) `g` group by `g`.`product_id`)) `i` left join `db_goods`.`t_goods_info` `g` on((`i`.`product_id` = `g`.`mark_id`))) group by `g`.`cooker`) union all (select `g`.`cooker` AS `cooker`,sum((`i`.`quantity` * `m`.`quantity`)) AS `quantity` from ((((select `m`.`product_id` AS `product_id`,sum(`m`.`quantity`) AS `quantity` from (select `i`.`product_id` AS `product_id`,sum(`i`.`quantity`) AS `quantity` from `db_order`.`t_order_item` `i` where (`i`.`product_type` = 2) group by `i`.`product_id`) `m` group by `m`.`product_id`)) `i` left join `db_goods`.`t_meal_item` `m` on((`i`.`product_id` = `m`.`meal_id`))) left join `db_goods`.`t_goods_info` `g` on((`g`.`mark_id` = `m`.`goods_id`))) group by `g`.`cooker`)) `c` group by `c`.`cooker`;

-- ----------------------------
-- View structure for v_goods_base
-- ----------------------------
DROP VIEW IF EXISTS `v_goods_base`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_goods_base` AS select `s`.`mark_id` AS `markId`,(select group_concat(concat(`db_goods`.`t_specification_info`.`attr_name`,':',`db_goods`.`t_specification_info`.`attr_value`) separator ';') from `db_goods`.`t_specification_info` where find_in_set(`db_goods`.`t_specification_info`.`mark_id`,`s`.`specification_ids`)) AS `attrList`,`i`.`name` AS `depotName`,`s`.`server_status` AS `serverStatus`,`gs`.`base_price` AS `basePrice`,`gs`.`sale_price` AS `salePrice`,`s`.`total_stock` AS `totalStock`,`s`.`current_stock` AS `currentStock`,`g`.`goods_name` AS `goodsName`,(select `db_base`.`t_attribute_info`.`name` from `db_base`.`t_attribute_info` where (`db_base`.`t_attribute_info`.`code` = `g`.`cook_style`)) AS `goodsStyle`,`g`.`server_type` AS `supplyType`,(select `db_base`.`t_attribute_info`.`name` from `db_base`.`t_attribute_info` where (`db_base`.`t_attribute_info`.`code` = `g`.`server_status`)) AS `goodsStatus`,(select `db_goods`.`t_goods_type`.`type_name` from `db_goods`.`t_goods_type` where (`db_goods`.`t_goods_type`.`mark_id` = `g`.`type_id`)) AS `typeName`,`g`.`base_price` AS `singleBasePrice`,`g`.`sale_price` AS `singleSalePrice`,(select `db_base`.`t_attribute_info`.`name` from `db_base`.`t_attribute_info` where (`db_base`.`t_attribute_info`.`code` = `g`.`unit`)) AS `unit`,`g`.`upper_time` AS `upperTime`,`g`.`down_time` AS `downTime`,`g`.`pre_upper_time` AS `preUpperTime`,`g`.`pre_down_time` AS `preDownTime`,`g`.`create_time` AS `createTime`,`g`.`modify_time` AS `modifyTime`,(select `db_user`.`t_user_info`.`nick_name` from `db_user`.`t_user_info` where (`db_user`.`t_user_info`.`mark_id` = `g`.`modifier`)) AS `modifierName`,(select `db_user`.`t_user_info`.`nick_name` from `db_user`.`t_user_info` where (`db_user`.`t_user_info`.`mark_id` = `g`.`creator`)) AS `creatorName`,(select `db_user`.`t_user_info`.`nick_name` from `db_user`.`t_user_info` where (`db_user`.`t_user_info`.`mark_id` = `g`.`cooker`)) AS `cookerName`,(select `db_goods`.`t_category_info`.`name` from `db_goods`.`t_category_info` where (`db_goods`.`t_category_info`.`mark_id` = `g`.`category_id`)) AS `categoryName`,(select `db_goods`.`t_brand_info`.`cn_name` from `db_goods`.`t_brand_info` where (`db_goods`.`t_brand_info`.`mark_id` = `g`.`brand_id`)) AS `brandName`,`a`.`province` AS `province`,`a`.`city` AS `city`,`a`.`area` AS `area` from ((((`db_goods`.`t_goods_stock` `s` left join `db_goods`.`t_goods_info` `g` on((`s`.`goods_id` = `g`.`mark_id`))) left join `db_goods`.`t_goods_specification` `gs` on(((`s`.`goods_id` = `gs`.`goods_id`) and (`s`.`specification_ids` = `gs`.`specification_ids`)))) left join `db_goods`.`t_storehouse_info` `i` on((`s`.`storehouse_id` = `i`.`mark_id`))) left join `db_goods`.`t_delivery_area` `a` on((`i`.`mark_id` = `a`.`storehouse_id`)));

-- ----------------------------
-- View structure for v_purchase_data
-- ----------------------------
DROP VIEW IF EXISTS `v_purchase_data`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_purchase_data` AS select `o`.`mark_id` AS `orderId`,`o`.`order_status` AS `orderStatus`,`t`.`product_name` AS `goodsName`,`t`.`quantity` AS `goodsCount`,`f`.`food_name` AS `foodName`,`i`.`food_id` AS `foodId`,sum(((`i`.`use_size` * `f`.`purchase_rate`) * `t`.`quantity`)) AS `foodConsume` from (((`db_order`.`t_order_info` `o` join `db_order`.`t_order_item` `t` on((`t`.`order_id` = `o`.`mark_id`))) join `db_goods`.`t_food_item` `i` on(((`i`.`goods_id` = `t`.`product_id`) and (`i`.`specification_ids` = `t`.`specification_ids`)))) join `db_goods`.`t_food_info` `f` on((`i`.`food_id` = `f`.`mark_id`))) where ((`t`.`product_type` = 0) and (`o`.`order_status` in ('OT02','OT03')) and (isnull(`o`.`delivery_date`) or (to_days(`o`.`delivery_date`) <= to_days(now()))) and (`i`.`server_status` = 1)) group by `o`.`mark_id`,`i`.`food_id` union all select `o`.`mark_id` AS `orderId`,`o`.`order_status` AS `orderStatus`,`t`.`product_name` AS `goodsName`,`t`.`quantity` AS `goodsCount`,`f`.`food_name` AS `foodName`,`i`.`food_id` AS `foodId`,sum(((`i`.`use_size` * `f`.`purchase_rate`) * `t`.`quantity`)) AS `foodConsume` from ((((`db_order`.`t_order_info` `o` join `db_order`.`t_order_item` `t` on((`t`.`order_id` = `o`.`mark_id`))) join `db_goods`.`t_meal_item` `m` on((`m`.`meal_id` = `t`.`product_id`))) join `db_goods`.`t_food_item` `i` on(((`i`.`goods_id` = `m`.`goods_id`) and (`i`.`specification_ids` = `m`.`specification_ids`)))) join `db_goods`.`t_food_info` `f` on((`f`.`mark_id` = `i`.`food_id`))) where ((`t`.`product_type` = 2) and (`o`.`order_status` in ('OT02','OT03')) and (isnull(`o`.`delivery_date`) or (to_days(`o`.`delivery_date`) <= to_days(now()))) and (`i`.`server_status` = 1)) group by `o`.`mark_id`,`i`.`food_id` union all select `o`.`mark_id` AS `orderId`,`o`.`order_status` AS `orderStatus`,`o`.`goods_name` AS `goodsName`,`o`.`quantity` AS `goodsCount`,`f`.`food_name` AS `foodName`,`i`.`food_id` AS `foodId`,sum(((`i`.`use_size` * `f`.`purchase_rate`) * `o`.`quantity`)) AS `foodConsume` from ((`db_order`.`t_seckill_order` `o` join `db_goods`.`t_food_item` `i` on(((`i`.`goods_id` = `o`.`goods_id`) and (`i`.`specification_ids` = `o`.`specification_ids`)))) join `db_goods`.`t_food_info` `f` on((`i`.`food_id` = `f`.`mark_id`))) where ((`o`.`order_status` in ('OT02','OT03')) and (isnull(`o`.`delivery_date`) or (to_days(`o`.`delivery_date`) <= to_days(now()))) and (`i`.`server_status` = 1)) group by `o`.`mark_id`,`i`.`food_id` union all select `o`.`mark_id` AS `orderId`,`o`.`order_status` AS `orderStatus`,`o`.`goods_name` AS `goodsName`,`o`.`quantity` AS `goodsCount`,`f`.`food_name` AS `foodName`,`i`.`food_id` AS `foodId`,sum(((`i`.`use_size` * `f`.`purchase_rate`) * `o`.`quantity`)) AS `foodConsume` from (((`db_order`.`t_teambuy_order` `o` join `db_order`.`t_teambuy_group` `g` on((`g`.`mark_id` = `o`.`group_id`))) join `db_goods`.`t_food_item` `i` on(((`i`.`goods_id` = `o`.`goods_id`) and (`i`.`specification_ids` = `o`.`specification_ids`)))) join `db_goods`.`t_food_info` `f` on((`i`.`food_id` = `f`.`mark_id`))) where ((`g`.`group_status` = 'GT02') and (`o`.`order_status` in ('OT02','OT03')) and (isnull(`o`.`delivery_date`) or (to_days(`o`.`delivery_date`) <= to_days(now()))) and (`i`.`server_status` = 1)) group by `o`.`mark_id`,`i`.`food_id`;

SET FOREIGN_KEY_CHECKS = 1;
